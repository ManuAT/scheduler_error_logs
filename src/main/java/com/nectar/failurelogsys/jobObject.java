package com.nectar.failurelogsys;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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

    // @Autowired
	// private HistoryRepository historyRepository;

    private static String schedulerName;
    private static String equipmentName;
    private static final Logger log = LoggerFactory.getLogger("jobObject");
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            // printing job Details
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            schedulerName = id;
            // System.out.println("Job Name :"+id+" executing");
            log.info("Job Name :"+id+" executing");


            // taking previous hour time stamps
            String startTime,previousStartTime,endTime;
            DateTime dateTime = new DateTime();
            dateTime = dateTime.minusHours(1);
            int currentHour = dateTime.getHourOfDay();
            dateTime = dateTime.millisOfDay().withMinimumValue();
            previousStartTime = dateTime.plusHours(currentHour-1).toString();
            startTime = dateTime.plusHours(currentHour).toString();
            endTime = dateTime.plusHours(currentHour+1).toString();
            System.out.println("StartTime :"+startTime+"EndTime :"+endTime);

            // List of equpment Name from file or Db

            // String[] equpmetList={"Equip 1","Equip 2","Equip 3","Equip 4","Equip 6"};  
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

            // getting data from History_db
            List<HistoryData> historyDataList= (List<HistoryData>) historyDataRepository.selectFromHistoryData(startTime, endTime);
            // System.out.println(historyDataList);

            for(String equipmentName:equipmentList){
                jobObject.equipmentName =equipmentName;
                int count = 0;
                int maxValue=0;
                int minValue=0;
                // HistoryData maxValue = new HistoryData();
                // maxValue.setData("0");
                // HistoryData minValue = new HistoryData();
                // minValue.setData("0");

                
                for(HistoryData data:historyDataList) {
                    // System.out.println(data.getName()+equpmetList[i]);
                    if(data.getName().equals(equipmentName)){
                        count ++;
                        if(count == 1){
                            minValue = Integer.parseInt(data.getData());
                            maxValue = Integer.parseInt(data.getData());
                        }
                        else{
                            maxValue = Integer.parseInt(data.getData());
                        }
                    }
                }

                // need to be checked

                // maxValue = historyDataList.stream().max(Comparator.comparing(HistoryData::getData)).orElse(new HistoryData());
                // minValue = historyDataList.stream().min(Comparator.comparing(HistoryData::getData)).orElse(new HistoryData());


                // if(maxValue.getData() == null || minValue.getData() ==null){
                //     maxValue.setData("0");
                //     minValue.setData("0");
                // }

                // System.out.println("Printing Max value"+maxValue.getData()+minValue.getData());
                System.out.println("Printing Max value"+maxValue+" "+minValue);
                    
                if(count>1){
                    // int aggregationDataFromHistory =Integer.parseInt(maxValue.getData())- Integer.parseInt(minValue.getData());
                    int aggregationDataFromHistory = maxValue = minValue;



                    AggregationData aggregationDataFromdb = aggregationDataRepository.selectFromAggregationData(previousStartTime, equipmentName);//[0]
                    if (aggregationDataFromdb == null){
                        aggregationDataFromdb = new AggregationData();
                        aggregationDataFromdb.setConsumption("0");
                    }
                        
                    if(aggregationDataFromHistory < 5*Integer.parseInt(aggregationDataFromdb.getConsumption()) || Integer.parseInt(aggregationDataFromdb.getConsumption()) ==0 )
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,Integer.toString(aggregationDataFromHistory));
                        // historyRepository.insertToAggregation(aggregationData);
                        aggregationDataRepository.save(aggregationData);
                        log.info("Aggregation Database updated");
                    }
                    else
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                        aggregationDataRepository.save(aggregationData);
                        log.warn("Aggregation Database updated with spike");

                        ErrorLog errorLog = new ErrorLog(new DateTime().toString(),id,notificationMessage.SPIKE.getMessage(),notificationMessage.SPIKE.getDescription(),equipmentName);
                        errorLogRepository.save(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");


                    }
                }
                else if(count==1){
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                        aggregationDataRepository.save(aggregationData);
                        log.warn("Equipment :"+equipmentName+"might lost Conection and databse updated");

                        ErrorLog errorLog = new ErrorLog(new DateTime().toString(),id,notificationMessage.NON_CUMULATIVE.getMessage(),notificationMessage.NON_CUMULATIVE.getDescription(),equipmentName);
                        errorLogRepository.save(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }else{
                    AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                    aggregationDataRepository.save(aggregationData);
                    log.warn("Equipment :"+equipmentName+" failed and aggregation database updated");

                    ErrorLog errorLog = new ErrorLog(new DateTime().toString(),id,notificationMessage.NO_COMMUNICATION.getMessage(),notificationMessage.NO_COMMUNICATION.getDescription(),equipmentName);
                    errorLogRepository.save(errorLog);
                    log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }

            }

        } catch (Exception e) {

            log.error("Error occured at scheduler and reporting :"+e.getMessage());
            ErrorLog errorLog = new ErrorLog(new DateTime().toString(),schedulerName,notificationMessage.SCHEDULER_ERROR.getMessage(),e.getMessage(),equipmentName);
            errorLogRepository.save(errorLog);
            throw new JobExecutionException(e);
        }
        
    }
    
}
