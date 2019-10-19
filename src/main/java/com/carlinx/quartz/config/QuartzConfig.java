package com.carlinx.quartz.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class QuartzConfig {

    //配置数据源
    @Bean
    public DataSource getDruidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://*****************:3306/quartz");
        druidDataSource.setUsername("*****");
        druidDataSource.setPassword("******");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }

    //配置properies配置文件
    @Bean
    public Properties getQuartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //等配置文件全部读取完成并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    //配置任务调度工厂
    @Bean
    public SchedulerFactoryBean getScheduleFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //开启更新job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //配置数据源
        schedulerFactoryBean.setDataSource(getDruidDataSource());
        //配置实例在spring中的key
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("quartz-schedule");
        //配置配置文件
        schedulerFactoryBean.setQuartzProperties(getQuartzProperties());
        //配置线程池
        schedulerFactoryBean.setTaskExecutor(getSchedulerThreadPool());
        return schedulerFactoryBean;
    }



    //配置线程池
    @Bean
    public Executor getSchedulerThreadPool(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(50);
        return threadPoolTaskExecutor;
    }


    //开启当前的任务调度器
    @Bean
    public Scheduler getSchedule() throws IOException, SchedulerException {
        Scheduler scheduler = getScheduleFactoryBean().getScheduler();
        scheduler.start();
        return scheduler;
    }







}
