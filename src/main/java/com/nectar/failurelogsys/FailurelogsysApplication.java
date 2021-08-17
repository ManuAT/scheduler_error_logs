package com.nectar.failurelogsys;


import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nectar.failurelogsys.job.utils.TaskScheduler;

@SpringBootApplication
public class FailurelogsysApplication implements CommandLineRunner{
	// config for scheduler
	private static final Logger LOGGER = LoggerFactory.getLogger("FailurelogsysApplication");
	private static final String AGGREGRATION_JOB = "findConsumption";
	private static final String EVERY_HOUR = " 0/10 * * * * ? *";

	@Autowired
	TaskScheduler taskScheduler;



	public static void main(String[] args) {
		SpringApplication.run(FailurelogsysApplication.class, args);

		
	}
  
	@Override
	public void run(String... args) throws Exception {

		// ============ job creation and delete =================

		// NOTE: need to command this code after first creation 

		// for testinf it uses 10s trigger

		// HashMap<String, Object> data = new HashMap<String, Object>();
		// data.put("domain", "nectar");
		// taskScheduler.createCroneJob(AGGREGRATION_JOB, AGGREGRATION_JOB, data, EVERY_HOUR,
		// 			jobObject.class);

		
		//NOTE to delete scheduler 

		// taskScheduler.deleteScheduledTask(AGGREGRATION_JOB, AGGREGRATION_JOB);
	
	}

}
