package com.carlinx.quartz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "定时任务实体")
public class JobDTO implements Serializable {
    @ApiModelProperty(value = "任务名称",dataType = "String")
    private String jobName;
    @ApiModelProperty(value = "任务组",dataType = "String")
    private String jobGroupName;
    @ApiModelProperty(value = "任务描述",dataType = "String")
    private String jobDescription;
    @ApiModelProperty(value = "触发器名称",dataType = "String")
    private String triggerName;
    @ApiModelProperty(value = "触发器组",dataType = "String")
    private String triggerGroupName;
    @ApiModelProperty(value = "cron表达式",dataType = "String")
    private String cron;
    @ApiModelProperty(value = "任务类全类名",dataType = "String")
    private String jobClassName;


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
}
