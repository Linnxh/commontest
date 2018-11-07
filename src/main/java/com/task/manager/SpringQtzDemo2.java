package com.task.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpringQtzDemo2 extends QuartzJobBean {

    private static final Log log = LogFactory.getLog(SpringQtzDemo2.class);

    static Logger logger = LoggerFactory.getLogger(SpringQtzDemo2.class);

    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();

//        System.out.println(" SpringQtzDemo start  执行");
        System.out.println("SpringQtzDemo2**********" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms)) + "  " + "(" + counter++ + ")");

    }
}
