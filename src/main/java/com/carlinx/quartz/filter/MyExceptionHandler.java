package com.carlinx.quartz.filter;

import com.carlinx.quartz.common.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult GlobalExceptionHandler(HttpServletRequest request, Exception e){
        return JsonResult.fail(e.getMessage());
    }




}
