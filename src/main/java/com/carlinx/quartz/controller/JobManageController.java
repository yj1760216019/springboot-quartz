package com.carlinx.quartz.controller;


import com.carlinx.quartz.common.JsonResult;
import com.carlinx.quartz.manage.TaskManager;
import com.carlinx.quartz.entity.JobDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@Api("定时任务管理")
public class JobManageController {

    @Autowired
    private TaskManager taskManager;


    @PostMapping("/addJob")
    @ApiOperation("添加任务")
    public JsonResult<Boolean> addJob(@ApiParam("任务实体")@RequestBody JobDTO jobDTO){
        taskManager.addJob(jobDTO.getJobName(),jobDTO.getJobGroupName(),jobDTO.getJobDescription(),jobDTO.getTriggerName(),jobDTO.getTriggerGroupName(),jobDTO.getCron(),jobDTO.getJobClassName());
        return JsonResult.success(true);
    }


    @PutMapping("/updateJob")
    @ApiOperation("修改任务时间")
    public JsonResult updateJob(@RequestBody JobDTO jobDTO){
        taskManager.updateJobTime(jobDTO.getTriggerName(),jobDTO.getTriggerGroupName(),jobDTO.getCron());
        return JsonResult.success(true);
    }


    @PutMapping("/removeJob")
    @ApiOperation("删除任务")
    public JsonResult removeJob(@ApiParam("任务实体")@RequestBody JobDTO jobDTO){
        taskManager.removeJob(jobDTO.getJobName(),jobDTO.getJobGroupName(),jobDTO.getTriggerName(),jobDTO.getTriggerGroupName());
        return JsonResult.success(true);
    }


    @PutMapping("/pauseJob/{jobName}/{jobGroupName}")
    @ApiOperation("暂停任务")
    public JsonResult pauseJob(@ApiParam("任务名")@PathVariable(value = "jobName")String jobName,
                               @ApiParam("任务组")@PathVariable(value = "jobGroupName")String jobGroupName){
        taskManager.pauseJob(jobName,jobGroupName);
        return JsonResult.success(true);
    }


    @PutMapping("/resumeJob/{jobName}/{jobGroupName}")
    @ApiOperation("恢复任务")
    public JsonResult resumeJob(@ApiParam("任务名")@PathVariable(value = "jobName") String jobName,
                                @ApiParam("任务组")@PathVariable(value = "jobGroupName") String jobGroupName){
        taskManager.resumeJob(jobName,jobGroupName);
        return JsonResult.success(true);
    }


    @PutMapping("/startSchedule")
    @ApiOperation("开始调度任务")
    public JsonResult startSchedule(){
        taskManager.startSchedule();
        return JsonResult.success(true);
    }



    @PutMapping("/shutdownSchedule")
    @ApiOperation("关闭调度任务")
    public JsonResult shutdownSchedule(){
        taskManager.shutdownSchedule();
        return JsonResult.success(true);
    }




}
