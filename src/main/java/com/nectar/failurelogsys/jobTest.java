package com.nectar.failurelogsys;

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
            
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            System.out.println("Job Name :"+id+" executing");
            String startTime,endTime;
            LocalDateTime localDate = new LocalDateTime();
            localDate = localDate.minusHours(1);
            int currentHour = localDate.getHourOfDay();
            localDate = localDate.millisOfDay().withMinimumValue();
            startTime = localDate.plusHours(currentHour).toString();
            endTime = localDate.plusHours(currentHour+1).toString();
            System.out.println("StartTime :"+startTime+"EndTime :"+endTime);
            System.out.println( historyRepository.selectFromHistoryData(startTime, endTime));



        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
        
    }
    
}
