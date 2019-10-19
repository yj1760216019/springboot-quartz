package com.carlinx.quartz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CronUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");


    public static String dataParseCron(Date date){
        String cronExpression = null;
        if(date != null){
            cronExpression = dateFormat.format(date);
        }
        return cronExpression;
    }
}
