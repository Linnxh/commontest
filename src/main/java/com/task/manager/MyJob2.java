package com.task.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob2 implements Job {

    public static final String JOB_NAME="job_name";
    public static final String JOB_GROUP="job_group";
    public static final String JOB_TRIGGER="job_trigger";
    public static final String JOB_TRIGGER_PARAM="job_trigger_param";
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            System.out.println("----执行------"+new SimpleDateFormat("yyyy-MM-dd HH:mm:dd:ss").format(new Date()));
            JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
            String jobName=(String) jobDataMap.get(MyJob2.JOB_NAME);
            Class<?> classzz=(Class<?>) jobDataMap.get(MyJob2.JOB_GROUP);
            Method method=(Method) jobDataMap.get(MyJob2.JOB_TRIGGER);
            Object[] objects=(Object[]) jobDataMap.get(MyJob2.JOB_TRIGGER_PARAM);
            method.invoke(classzz.newInstance(), objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
