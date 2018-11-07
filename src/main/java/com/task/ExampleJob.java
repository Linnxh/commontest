package com.task;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 原文：https://blog.csdn.net/u010397369/article/details/17465649
 * 单机部署的quartz，配置比较简单不建议采用
 */
public class ExampleJob extends QuartzJobBean {

    private int timeout;

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
//        System.out.println("ExampleJob~~~" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "执行ExampleJob的定时任务");
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }


}
