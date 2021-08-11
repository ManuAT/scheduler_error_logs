package com.nectar.failurelogsys.job.utils;

import static java.time.ZoneId.systemDefault;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @since April 2020
 * @author Aathira Babu
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TaskScheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskScheduler.class);
	Properties props = new Properties();

	@Autowired
	private  Scheduler scheduler;

	public void deleteScheduledTask(String jobName, String group) {
		try {
			scheduler.deleteJob(jobKey(jobName, group));
		} catch (SchedulerException e) {
			LOGGER.error("Error occurred while deleting scheduled task {}", jobName, e);
		}
	}

	public void createCroneJob(String jobName, String group, HashMap<String, Object> data, String expression,
			Class<? extends Job> jobClass) {

		CronTrigger trigger = newTrigger()
				.withIdentity(jobName, group).withSchedule(cronSchedule(expression)
						.withMisfireHandlingInstructionDoNothing().inTimeZone(TimeZone.getTimeZone(systemDefault())))
				.usingJobData("cron", expression).build();

		JobDataMap jobDataMap = data == null ? new JobDataMap(new LinkedHashMap<>()) : new JobDataMap(data);
		JobDetail jobDetails = newJob(jobClass).withIdentity(jobName, group).usingJobData(jobDataMap).build();
		try {
			scheduler.scheduleJob(jobDetails, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void listScheduledTask() {

		try {
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
					String name = jobKey.getName();
					String group = jobKey.getGroup();
					JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
					List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

					System.out.println("[jobName] : " + name + " [groupName] : " + group + " [nextRun] "
							+ triggers.get(0).getNextFireTime() + " [lastRun] "
							+ triggers.get(0).getPreviousFireTime());
				}
			}
		} catch (SchedulerException e) {
			LOGGER.error("Could not find all jobs due to error - {}", e.getLocalizedMessage());
		}
	}

	public void listScheduledTask(String groupName) {

		try {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				String name = jobKey.getName();
				String group = jobKey.getGroup();
				JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
				System.out.println("[jobName] : " + name + " [groupName] : " + group + " [nextRun] "
						+ triggers.get(0).getNextFireTime() + " [lastRun] " + triggers.get(0).getPreviousFireTime());
			}
		} catch (SchedulerException e) {
			LOGGER.error("Could not find all jobs due to error - {}", e.getLocalizedMessage());
		}
	}

	public void pauseJob(String group, String name) {
		try {
			scheduler.pauseJob(jobKey(name, group));
			LOGGER.info("Paused job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			LOGGER.error("Could not pause job with key - {}.{} due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}

	public void resumeJob(String group, String name) {
		try {
			scheduler.resumeJob(jobKey(name, group));
			LOGGER.info("Resumed job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			LOGGER.error("Could not resume job with key - {}.{} due to error - {}", group, name,
					e.getLocalizedMessage());
		}
	}
}

