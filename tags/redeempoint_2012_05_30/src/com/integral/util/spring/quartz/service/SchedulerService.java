package com.integral.util.spring.quartz.service;

import java.util.Date;

import org.quartz.CronExpression;

public interface SchedulerService {
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param cronExpression  Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param name  Quartz CronTrigger名称
	 * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String name,String cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param cronExpression Quartz CronExpression
	 */
	void schedule(CronExpression cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param name Quartz CronTrigger名称
	 * @param cronExpression Quartz CronExpression
	 */
	void schedule(String name,CronExpression cronExpression);
	
	/**
	 * 在startTime时执行调试一次
	 * @param startTime 调度开始时间
	 */
	void schedule(Date startTime);	
	
	/**
	 * 在startTime时执行调试一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 */
	void schedule(String name,Date startTime);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */
	void schedule(Date startTime,Date endTime);	
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */
	void schedule(String name,Date startTime,Date endTime);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */
	void schedule(Date startTime,Date endTime,int repeatCount);	
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */
	void schedule(String name,Date startTime,Date endTime,int repeatCount);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */
	void schedule(Date startTime,Date endTime,int repeatCount,long repeatInterval) ;
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */
	void schedule(String name,Date startTime,Date endTime,int repeatCount,long repeatInterval);
}
