package com.nectar.failurelogsys;


import java.util.HashMap;
import java.util.Scanner;

import com.nectar.failurelogsys.db.model.AggregationData;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.repository.HistoryRepository;
import com.nectar.failurelogsys.job.utils.TaskScheduler;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FailurelogsysApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger("FailurelogsysApplication");
	private static final String AGGREGRATION_JOB = "findConsumption";
	private static final String EVERY_HOUR = " 0/10 * * * * ? *";

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	TaskScheduler taskScheduler;



	public static void main(String[] args) {
		SpringApplication.run(FailurelogsysApplication.class, args);

		
	}

	@Override
	public void run(String... args) throws Exception {

		// =========== testing Database insertion ==========

		// System.out.println("inside run ....");
		// HistoryData historyData = new HistoryData("Equp2","hello", "11001");
		// historyRepository.insert(historyData);

		// ErrorLog errorLog = new ErrorLog("Equp2","hello", "11001", "hello");
		// historyRepository.insertToErrorLog(errorLog);

		// AggregationData aggregationData = new AggregationData("Equp2","hello", "11001");
		// historyRepository.insertToAggregation(aggregationData);

		// System.out.println("History record inserted successfully in first database");

		// =============================
		// String startTime,endTime;
		// LocalDateTime localDate = new LocalDateTime();
		// localDate = localDate.minusHours(1);
		// int currentHour = localDate.getHourOfDay();
		// localDate = localDate.millisOfDay().withMinimumValue();
		// startTime = localDate.plusHours(currentHour).toString();
		// endTime = localDate.plusHours(currentHour+1).toString();
		// System.out.println("StartTime :"+startTime+"EndTime :"+endTime);
		// System.out.println( historyRepository.selectFromHistoryData(startTime, endTime));
		// System.out.println( historyRepository.selectFromHistoryData("2021-08-12 07:06:51", "2021-08-12 08:06:51"));

		// ============ job creation and delete =================

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("domain", "nectar");
		taskScheduler.createCroneJob(AGGREGRATION_JOB, AGGREGRATION_JOB, data, EVERY_HOUR,
					jobObject.class);
		
		// taskScheduler.deleteScheduledTask(AGGREGRATION_JOB, AGGREGRATION_JOB);

		// =============== UI setup ==============

	// 	Scanner sc = new Scanner(System.in);

	// 	System.out.println("Enter option");
	// 	System.out.println("1.Create Weather Prediction Job");
	// 	System.out.println("2.Delete Weather Prediction Job");
		
	// 	String optionStr = sc.next();
	// 	int option = Integer.parseInt(optionStr);

	// 	HashMap<String, Object> data = new HashMap<String, Object>();

	// 	String domain = null;
	// 	if (option == 1 || option == 3) {
	// 		System.out.println("Enter domain");
	// 		domain = sc.next();
	// 		data.put("domain", domain);
	// 	}

	// 	switch (option) {

	// 		case 1:
	// 			taskScheduler.createCroneJob(AGGREGRATION_JOB, AGGREGRATION_JOB, data, EVERY_HOUR,
	// 			jobObject.class);
	// 			break;
	
	// 		case 2:
	// 			taskScheduler.deleteScheduledTask(AGGREGRATION_JOB, AGGREGRATION_JOB);
	// 			break;
	// 		default:
	// 			break;
	// 		}


	// 		sc.close();
	// 		System.exit(0);
		
	// }

	
	}

}
