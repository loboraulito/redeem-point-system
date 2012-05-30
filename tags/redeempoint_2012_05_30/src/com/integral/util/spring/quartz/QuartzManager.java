package com.integral.util.spring.quartz;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


/**
 * @author Jason
 * Quartz框架组件
 */
public class QuartzManager {
	private Logger logger = Logger.getLogger(QuartzManager.class);
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = Scheduler.DEFAULT_GROUP;
	private static String TRIGGER_GROUP_NAME = Scheduler.DEFAULT_GROUP;
	
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
	 * @param jobName 任务名
	 * @param jobClass 任务
	 * @param time 时间设置，参考quartz说明文档(Cron 表达式)
	 */
	public static void addJob(String jobName, Class jobClass, String time) {
		try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, jobClass);// 任务名，任务组，任务执行类  
            // 触发器  
            CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组  
            trigger.setCronExpression(time);// 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
            // 启动  
            if (!sched.isShutdown()){  
                sched.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } 
	}
	/**
	 * 添加一个定时任务 
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param triggerName 触发器名 
	 * @param triggerGroupName 触发器组名
	 * @param jobClass 任务
	 * @param time 时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName, String jobClass, String time){
		try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, Class.forName(jobClass));// 任务名，任务组，任务执行类  
            // 触发器  
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组  
            trigger.setCronExpression(time);// 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } 
	}
	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
	 * @param jobName 任务名
	 * @param time 时间设置，参考quartz说明文档
	 */
	public static void modifyJobTime(String jobName, String time) {
		try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);  
            if(trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);  
                Class objJobClass = jobDetail.getJobClass();  
                removeJob(jobName);
  
                addJob(jobName, objJobClass, time);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
	}
	/**
	 * 修改一个任务的触发时间 
	 * @param triggerName 触发器名 
	 * @param triggerGroupName 触发器组名
	 * @param time 时间设置，参考quartz说明文档
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName,
					triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
	 * @param jobName 任务名
	 */
	public static void removeJob(String jobName) {
		try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器  
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器  
            sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
	}
	/**
	 *  移除一个任务 
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param triggerName 触发器名 
	 * @param triggerGroupName 触发器组名
	 */
	public static void removeJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName) { 
		try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器  
            sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器  
            sched.deleteJob(jobName, jobGroupName);// 删除任务  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
	}
	/**
	 * 启动所有任务
	 */
	public static void startJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
	
	/** 
     * 关闭所有定时任务 
     */  
    public static void shutdownJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            if(!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
}
