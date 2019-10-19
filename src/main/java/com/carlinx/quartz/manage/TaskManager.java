package com.carlinx.quartz.manage;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 添加调度任务
     * @param jobName
     * @param jobGroupName
     * @param jobDescription
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @param jobClassName
     */
    public void addJob(String jobName,String jobGroupName,String jobDescription,String triggerName,String triggerGroupName,String cron,String jobClassName){
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>)Class.forName(jobClassName);
            //构建作业实例
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)
                    .withDescription(jobDescription)
                    .build();
            //构建触发器实例
            CronTrigger triggerDetail = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    /**
                     * mifire处理规则(未执行方法处理规则)
                     *
                     * withMisfireHandlingInstructionIgnoreMisfires()：以错过第一频率时间开始执行 重做所有错过频率周期后 再按照正常的cron频率依次执行
                     *
                     * withMisfireHandlingInstructionDoNothing(): 不触发立即执行 会在下次Cron触发到达时刻开始按频率执行
                     *
                     * withMisfireHandlingInstructionFireAndProceed(): 以启动时间为触发频率立即执行一次触发  然后按照cron频率依次执行
                     *
                     */
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
            //添加调度任务

            //如果调度任务未启动  启动
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetail,triggerDetail);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("未找到名为:"+jobClassName+"的类，前检查类名");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }




    /**
     * 删除调度任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            //移除任务
            boolean result = scheduler.deleteJob(jobKey);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 修改任务调度时间
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     */
    public void updateJobTime(String triggerName,String triggerGroupName,String cron){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if(trigger == null){
                throw new RuntimeException("未查询到组名为:"+triggerGroupName+" 名称为:"+triggerName+" 的调度器");
            }
            CronTrigger cronTrigger = (CronTrigger)trigger;
            String cronExpression = cronTrigger.getCronExpression();
            if(!cronExpression.equalsIgnoreCase(cron)){
                //删除调度器
                CronTrigger newCronTrigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                        .build();
                //更新调度时间
                scheduler.rescheduleJob(triggerKey,newCronTrigger);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 暂停定时任务
     * @param jobName
     * @param jobGroupName
     */
    public void pauseJob(String jobName,String jobGroupName){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }



    /**
     * 恢复定时任务
     * @param jobName
     * @param jobGroupName
     */
    public void resumeJob(String jobName,String jobGroupName){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 开启调度任务
     */
    public void startSchedule(){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.start();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }



    /**
     * 关闭调度任务
     */
    public void shutdownSchedule(){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if(scheduler.isStarted()){
                scheduler.shutdown();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }








}
