package com.nectar.failurelogsys;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nectar.failurelogsys.db.repository.AggregationRepository;
import com.nectar.failurelogsys.db.repository.ErrorFinderRepository;
import com.nectar.failurelogsys.db.repository.HistoryRepository;
import com.nectar.failurelogsys.pojos.HistoryDataFetch;
import com.nectar.failurelogsys.service.EquipmentNotificationMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class jobObject {

	private EquipmentNotificationMessage notificationMessage;

	@Autowired
	private HistoryRepository historyDataRepository;

	@Autowired
	private AggregationRepository aggregationDataRepository;

	@Autowired
	private ErrorFinderRepository errorLogRepository;

	private static String schedulerName;
	private static final Logger log = LoggerFactory.getLogger("jobObject");

	public void getSchdulerFailureLogs() {
		try {

			// taking previous hour time stamps

			DateTime dateTime = new DateTime();
			dateTime = dateTime.minusHours(1);

			DateTime startProcessingTime = dateTime.withTime(dateTime.getHourOfDay(), 0, 0, 0);
			DateTime endProcessingTime = dateTime.withTime(dateTime.getHourOfDay(), 59, 59, 999);
			Timestamp startTime = new Timestamp(startProcessingTime.getMillis());
			Timestamp endTime = new Timestamp(endProcessingTime.getMillis());
			System.out.println("StartTime :" + startProcessingTime + "EndTime :" + endProcessingTime);

			// List of equpment Name from file or Db

			List<String> equipmentList = new ArrayList<String>();

			try {
				File file = new File("equipment.txt");

				Scanner sc = new Scanner(file);

				while (sc.hasNextLine())
					equipmentList.add(sc.nextLine());

				sc.close();
			} catch (Exception e) {
				log.error(e.toString());
			}

			
			// System.out.println(historyDataList);

			for (String equip : equipmentList) {
				int count = 0;
				// getting data from History_db
				Double minimum =0.0;
				Double maximum =0.0;
				List<HistoryDataFetch> historyDataList = historyDataRepository
						.findHistoryData(equip,startTime, endTime);
			

				Optional<HistoryDataFetch> minData = historyDataList.stream()
						.min(Comparator.comparing(HistoryDataFetch::getData));
				if (minData.isPresent()) {
					minimum	= minData.get().getData();

				}
			
				Optional<HistoryDataFetch> maxData = historyDataList.stream()
						.min(Comparator.comparing(HistoryDataFetch::getData));
				if (maxData.isPresent()) {
					maximum	= minData.get().getData();

				}
			

//				if (count > 1) {
//					int aggregationDataFromHistory = Integer.parseInt(maxValue.getData())
//							- Integer.parseInt(minValue.getData());
//					// int aggregationDataFromHistory = maxValue = minValue;
//
//					AggregationData aggregationDataFromdb = (AggregationData) aggregationDataRepository
//							.selectFromAggregationData(previousStartTime, equipmentName);// [0]
//
//					if (aggregationDataFromHistory < 5 * Integer.parseInt(aggregationDataFromdb.getConsumption())
//							|| Integer.parseInt(aggregationDataFromdb.getConsumption()) == 0) {
//						AggregationData aggregationData = new AggregationData(equipmentName, startTime,
//								Integer.toString(aggregationDataFromHistory));
//						// historyRepository.insertToAggregation(aggregationData);
//						aggregationDataRepository.save(aggregationData);
//						log.info("Aggregation Database updated");
//					} else {
//						AggregationData aggregationData = new AggregationData(equipmentName, startTime, "0");
//						aggregationDataRepository.save(aggregationData);
//						log.warn("Aggregation Database updated with spike");
//
//						ErrorLog errorLog = new ErrorLog(new DateTime().toString(), id,
//								notificationMessage.SPIKE.getMessage(), notificationMessage.SPIKE.getDescription());
//						errorLogRepository.save(errorLog);
//						log.warn("Equipment :" + equipmentName + " failed and err_databse database updated");
//
//					}
//				} else if (count == 1) {
//					AggregationData aggregationData = new AggregationData(equipmentName, startTime, "0");
//					aggregationDataRepository.save(aggregationData);
//					log.warn("Equipment :" + equipmentName + "might lost Conection and databse updated");
//
//					ErrorLog errorLog = new ErrorLog(new DateTime().toString(), id,
//							notificationMessage.NON_CUMULATIVE.getMessage(),
//							notificationMessage.NON_CUMULATIVE.getDescription());
//					errorLogRepository.save(errorLog);
//					log.warn("Equipment :" + equipmentName + " failed and err_databse database updated");
//
//				} else {
//					AggregationData aggregationData = new AggregationData(equipmentName, startTime, "0");
//					aggregationDataRepository.save(aggregationData);
//					log.warn("Equipment :" + equipmentName + " failed and aggregation database updated");
//
//					ErrorLog errorLog = new ErrorLog(new DateTime().toString(), id,
//							notificationMessage.NO_COMMUNICATION.getMessage(),
//							notificationMessage.NO_COMMUNICATION.getDescription());
//					errorLogRepository.save(errorLog);
//					log.warn("Equipment :" + equipmentName + " failed and err_databse database updated");
//
//				}

			}

		} catch (Exception e) {

			log.error("Error occured at scheduler and reporting :" + e.getMessage());
//			ErrorLog errorLog = new ErrorLog(new DateTime().toString(), schedulerName,
//					notificationMessage.SCHEDULER_ERROR.getMessage(), e.getMessage());
//			errorLogRepository.save(errorLog);
//			throw new JobExecutionException(e);
		}

	}

}
