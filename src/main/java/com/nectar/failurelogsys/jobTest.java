package com.nectar.failurelogsys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.nectar.failurelogsys.db.model.AggregationData;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.repository.HistoryRepository;
import com.nectar.failurelogsys.service.EquipmentNotificationMessage;

import org.joda.time.LocalDateTime;
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
public class jobTest extends QuartzJobBean{

    private EquipmentNotificationMessage notificationMessage;


	@Autowired
	private HistoryRepository historyRepository;

    private static String schedulerName;
    private static final Logger log = LoggerFactory.getLogger("jobTest");
    
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
            LocalDateTime localDate = new LocalDateTime();
            localDate = localDate.minusHours(1);
            int currentHour = localDate.getHourOfDay();
            localDate = localDate.millisOfDay().withMinimumValue();
            previousStartTime = localDate.plusHours(currentHour-1).toString();
            startTime = localDate.plusHours(currentHour).toString();
            endTime = localDate.plusHours(currentHour+1).toString();
            // System.out.println("StartTime :"+startTime+"EndTime :"+endTime);

            // List of equpment Name from file or Db

            // String[] equpmetList={"Equip 1","Equip 2","Equip 3","Equip 4","Equip 6"};  
            List<String> equipmentList = new ArrayList<String>();

            try{
                File file = new File("equipment.txt");
                
                Scanner sc = new Scanner(file);
              
                while (sc.hasNextLine())
                  equipmentList.add(sc.nextLine());
            
                // for(String data:equipmentList)
                //   log.info(data);

                }catch(Exception e){
                  log.warn(e.toString());
                }

            // getting data from History_db
            List<HistoryData> historyDataList= historyRepository.selectFromHistoryData(startTime, endTime);
            // System.out.println(historyDataList);

            for(String equipmentName:equipmentList){
                int count = 0;
                int maxValue=0;
                int minValue=0;
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

                if(count>1){
                    int aggregationDataFromHistory = maxValue-minValue;

                    AggregationData aggregationDataFromdb = historyRepository.selectFromAggregationData(previousStartTime, equipmentName);//[0]
                    
                    if(aggregationDataFromHistory < 5*Integer.parseInt(aggregationDataFromdb.getConsumption()) || Integer.parseInt(aggregationDataFromdb.getConsumption()) ==0 )
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,Integer.toString(aggregationDataFromHistory));
                        historyRepository.insertToAggregation(aggregationData);
                        log.info("Aggregation Database updated");
                    }
                    else
                    {
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                        historyRepository.insertToAggregation(aggregationData);
                        log.warn("Aggregation Database updated with spike");

                        ErrorLog errorLog = new ErrorLog(new LocalDateTime().toString(),id,notificationMessage.SPIKE.getMessage(),notificationMessage.SPIKE.getDescription());
                        historyRepository.insertToErrorLog(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");


                    }
                }
                else if(count==1){
                        AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                        historyRepository.insertToAggregation(aggregationData);
                        log.warn("Equipment :"+equipmentName+"might lost Conection and databse updated");

                        ErrorLog errorLog = new ErrorLog(new LocalDateTime().toString(),id,notificationMessage.NON_CUMULATIVE.getMessage(),notificationMessage.NON_CUMULATIVE.getDescription());
                        historyRepository.insertToErrorLog(errorLog);
                        log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }else{
                    AggregationData aggregationData = new AggregationData(equipmentName,startTime,"0");
                    historyRepository.insertToAggregation(aggregationData);
                    log.warn("Equipment :"+equipmentName+" failed and aggregation database updated");

                    ErrorLog errorLog = new ErrorLog(new LocalDateTime().toString(),id,notificationMessage.NO_COMMUNICATION.getMessage(),notificationMessage.NO_COMMUNICATION.getDescription());
                    historyRepository.insertToErrorLog(errorLog);
                    log.warn("Equipment :"+equipmentName+" failed and err_databse database updated");

                }

            }

        } catch (Exception e) {

            log.warn("Error occured at scheduler and reporting :"+e.getMessage());
            ErrorLog errorLog = new ErrorLog(new LocalDateTime().toString(),schedulerName,notificationMessage.SCHEDULER_ERROR.getMessage(),e.getMessage());
            historyRepository.insertToErrorLog(errorLog);
            throw new JobExecutionException(e);
        }
        
    }
    
}
