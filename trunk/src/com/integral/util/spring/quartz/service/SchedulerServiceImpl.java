package com.integral.util.spring.quartz.service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	private Scheduler scheduler;
	private JobDetail jobDetail;

	@Autowired
	public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void schedule(String cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		try {
			schedule(name, new CronExpression(cronExpression));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name, Scheduler.DEFAULT_GROUP, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
			scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, cronTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(Date startTime) {
		schedule(startTime, null);
	}

	@Override
	public void schedule(String name, Date startTime) {
		schedule(name, startTime, null);
	}

	@Override
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(null, startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount) {
		schedule(name, startTime, endTime, 0, 0L);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			SimpleTrigger SimpleTrigger = new SimpleTrigger(name, Scheduler.DEFAULT_GROUP, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP, startTime, endTime, repeatCount, repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, SimpleTrigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
