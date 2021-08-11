package com.nectar.failurelogsys;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class jobTest extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            System.out.println("Job Name :"+id+" executing");
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
        
    }
    
}
