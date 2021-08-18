package com.nectar.failurelogsys;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.nectar.failurelogsys.db.model.AggregationData;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.repository.AggregationDataRepository;
import com.nectar.failurelogsys.db.repository.ErrorLogRepository;
import com.nectar.failurelogsys.db.repository.HistoryDataRepository;
import com.nectar.failurelogsys.service.EquipmentNotificationMessage;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class jobObject extends QuartzJobBean{

    private EquipmentNotificationMessage notificationMessage;


	@Autowired
	private HistoryDataRepository historyDataRepository;

    @Autowired
	private AggregationDataRepository aggregationDataRepository;

    @Autowired
	private ErrorLogRepository errorLogRepository;


    private static String schedulerName;
    private static String equipmentName;
    private static final Logger log = LoggerFactory.getLogger("jobObject");
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            // printing job Details
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            schedulerName = id;
            log.info("Job Name :"+id+" executing");


            // taking previous hour time stamps
            Timestamp startTime,previousStartTime,endTime;
            DateTime dateTime = new DateTime();
            dateTime = dateTime.minusHours(1);
            int currentHour = dateTime.getHourOfDay();
            dateTime = dateTime.millisOfDay().withMinimumValue();
            previousStartTime = new Timestamp(dateTime.plusHours(currentHour-1).getMillis());
            startTime = new Timestamp(dateTime.plusHours(currentHour).getMillis());
            endTime = new Timestamp(dateTime.plusHours(currentHour+1).getMillis());
            System.out.println("StartTime :"+startTime+"EndTime :"+endTime);

            // List of equpment Name from file or Db
            List<String> equipmentList = new ArrayList<String>();

            try{
                File file = new File("equipment.txt");
                
                Scanner sc = new Scanner(file);
              
                while (sc.hasNextLine())
                  equipmentList.add(sc.nextLine());

                sc.close();
                }catch(Exception e){
                  log.warn(e.toString());
                }

            

            for(String equipmentName:equipmentList){
                jobObject.equipmentName =equipmentName;


                int count = 0;
               

                Double minimum =0.0;
				Double maximum =0.0;

                List<HistoryData> historyDataList =  historyDataRepository.selectFromHistoryData(startTime, endTime,equipmentName);

                count = historyDataList.size();
                System.out.println("Count :"+count);
                Optional<HistoryData> minData = historyDataList.stream()
						.min(Comparator.comparing(HistoryData::getData));

                        if (minData.isPresent()) {
                            minimum	= minData.get().getData();
                        }

                Optional<HistoryData> maxData = historyDataList.stream()
                    .max(Comparator.comparing(HistoryData::getData));

                        if (maxData.isPresent()) {
                            maximum	= maxData.get().getData();
                        }

 

                System.out.println("Printing Max value"+minimum+" "+maximum);
                    
                if(count>1){
                    double aggregationDataFromHistory = maximum - minimum;



                    AggregationData aggregationDataFromdb = aggregationDataRepository.selectFromAggregationData(previousStartTime, equipmentName);//[0]
                    if (aggregationDataFromdb == null){
                        aggregationDataFromdb = new AggregationData();
                        aggregationDataFromdb.setConsumption(0.0);
                    }
                        
                    if(aggregationDataFromHistory < 5*aggregationDataFromdb.getConsumption() || aggregationDataFromdb.getConsumption() ==0.0 )
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,aggregationDataFromHistory);
                        aggregationDataRepository.save(aggregationData);
                        log.info("Aggregation Database updated");
                    }
                    else
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,0.0);
                        aggregationDataRepository.save(aggregationData);
                        log.warn("Aggregation Database updated with spike");

                        ErrorLog errorLog = new ErrorLog(new Timestamp(new DateTime().getMillis()),id,notificationMessage.SPIKE.getMessage(),notificationMessage.SPIKE.getDescription(),equipmentName);
                        errorLogRepository.save(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");


                    }
                }
                else if(count==1){
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,0.0);
                        aggregationDataRepository.save(aggregationData);
                        log.warn("Equipment :"+equipmentName+"might lost Conection and databse updated");

                        ErrorLog errorLog = new ErrorLog(new Timestamp(new DateTime().getMillis()),id,notificationMessage.NON_CUMULATIVE.getMessage(),notificationMessage.NON_CUMULATIVE.getDescription(),equipmentName);
                        errorLogRepository.save(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }else{
                    AggregationData aggregationData = new AggregationData(equipmentName,startTime,0.0);
                    aggregationDataRepository.save(aggregationData);
                    log.warn("Equipment :"+equipmentName+" failed and aggregation database updated");

                    ErrorLog errorLog = new ErrorLog(new Timestamp(new DateTime().getMillis()),id,notificationMessage.NO_COMMUNICATION.getMessage(),notificationMessage.NO_COMMUNICATION.getDescription(),equipmentName);
                    errorLogRepository.save(errorLog);
                    log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }

            }

        } catch (Exception e) {

            log.error("Error occured at scheduler and reporting :"+e.getMessage());
            ErrorLog errorLog = new ErrorLog(new Timestamp(new DateTime().getMillis()),schedulerName,notificationMessage.SCHEDULER_ERROR.getMessage(),e.getMessage(),equipmentName);
            errorLogRepository.save(errorLog);
            throw new JobExecutionException(e);
        }
        
    }
    
}
