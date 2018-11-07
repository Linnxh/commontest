package com.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpringQtzDemo extends QuartzJobBean {

    private static final Log log = LogFactory.getLog(SpringQtzDemo.class);

    static Logger logger = LoggerFactory.getLogger(SpringQtzDemo.class);

    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();

//        System.out.println(" SpringQtzDemo start  执行");
        System.out.println("SpringQtzDemo**********" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms)) + "  " + "(" + counter++ + ")");

    }
}
