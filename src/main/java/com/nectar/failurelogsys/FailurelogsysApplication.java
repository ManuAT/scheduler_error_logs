package com.nectar.failurelogsys;


import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.repository.HistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FailurelogsysApplication implements CommandLineRunner{

	@Autowired
	private HistoryRepository historyRepository;



	public static void main(String[] args) {
		SpringApplication.run(FailurelogsysApplication.class, args);

		
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("inside run ....");
		HistoryData historyData = new HistoryData("Equp2","hello", "11001");
		historyRepository.insert(historyData);

		ErrorLog errorLog = new ErrorLog("Equp2","hello", "11001", "hello");
		historyRepository.insert2(errorLog);
		System.out.println("History record inserted successfully in first database");
	}

	


}
