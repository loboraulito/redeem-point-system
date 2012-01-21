package com.integral.util.spring.quartz;

import java.io.Serializable;
import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;

/** 
 * <p>Description: [动态设置JOB执行的时间]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class DynamicJobSchedule implements Serializable {
    
    private Scheduler scheduler;
    
    public Scheduler getScheduler() {
        return scheduler;
    }
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public DynamicJobSchedule(){
        
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param args
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public static void main(String[] args) {

    }
    /**
     * <p>Discription:[动态设置执行时间]</p>
     * @param newCronExpression 新的cron表达式
     * @throws SchedulerException
     * @throws ParseException
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void dynamicSchedule(String newCronExpression) throws SchedulerException, ParseException{
        CronTriggerBean trigger = (CronTriggerBean)this.scheduler.getTrigger("cronReportTrigger", Scheduler.DEFAULT_GROUP);
        if(!trigger.getCronExpression().equals(newCronExpression)){
            trigger.setCronExpression(newCronExpression);
            scheduler.rescheduleJob(trigger.getName(), Scheduler.DEFAULT_GROUP, trigger);
        }
    }
    /**
     * <p>Discription:[停止执行]</p>
     * @param triggerName
     * @author:[代超]
     * @throws SchedulerException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void stopTrigger(String triggerName) throws SchedulerException{
        this.scheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
    }
    /**
     * <p>Discription:[重启触发器]</p>
     * @param triggerName
     * @author:[代超]
     * @throws SchedulerException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void resumeTrigger(String triggerName) throws SchedulerException{
        this.scheduler.resumeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
    }
    /**
     * <p>Discription:[移除触发器]</p>
     * @param triggerName
     * @return
     * @throws SchedulerException
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean removeTrigger(String triggerName) throws SchedulerException{
        this.scheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
        return this.scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
    }
}
