package com.nectar.failurelogsys;

import java.util.List;

import com.nectar.failurelogsys.db.model.AggregationData;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.repository.HistoryRepository;

import org.joda.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class jobTest extends QuartzJobBean{

    
	@Autowired
	private HistoryRepository historyRepository;
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            // printing job Details
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            System.out.println("Job Name :"+id+" executing");

            // taking previous hour time stamps
            String startTime,endTime;
            LocalDateTime localDate = new LocalDateTime();
            localDate = localDate.minusHours(1);
            int currentHour = localDate.getHourOfDay();
            localDate = localDate.millisOfDay().withMinimumValue();
            startTime = localDate.plusHours(currentHour).toString();
            endTime = localDate.plusHours(currentHour+1).toString();
            System.out.println("StartTime :"+startTime+"EndTime :"+endTime);

            // List of equpment Name from file or Db

            String[] equpmetList={"Equip 1","Equip 2","Equip 3","Equip 4","Equip 6"};  

            // getting data from History_db
            List<HistoryData> historyDataList= historyRepository.selectFromHistoryData(startTime, endTime);
            // System.out.println(historyDataList);

            for(int i=0;i<equpmetList.length;i++){
                int count = 0;
                int maxValue=0;
                int minValue=0;
                for(HistoryData data:historyDataList) {
                    // System.out.println(data.getName()+equpmetList[i]);
                    if(data.getName().equals(equpmetList[i])){
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
                    
                    AggregationData aggregationData = new AggregationData(equpmetList[i],startTime,Integer.toString(aggregationDataFromHistory));
                    historyRepository.insertToAggregation(aggregationData);
                    System.out.println("Aggregation Database updated");
                }
                else if(count==1){
                        System.out.println("Equipment :"+equpmetList[i]+"might lost Conection");
                        AggregationData aggregationData = new AggregationData(equpmetList[i],startTime,"0");
                        historyRepository.insertToAggregation(aggregationData);
                }else{
                    System.out.println("Equipment :"+equpmetList[i]+" failed");
                    AggregationData aggregationData = new AggregationData(equpmetList[i],startTime,"0");
                    historyRepository.insertToAggregation(aggregationData);
                }

            }

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
        
    }
    
}
