package com.nectar.failurelogsys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FailurelogsysApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger("FailurelogsysApplication");
	private static final String AGGREGRATION_JOB = "findConsumption";
	private static final String EVERY_HOUR = " 0/10 * * * * ? *";


	public static void main(String[] args) {
		SpringApplication.run(FailurelogsysApplication.class, args);

	}

}
