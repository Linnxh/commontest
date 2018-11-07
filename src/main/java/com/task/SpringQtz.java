package com.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 集群部署的quartz，配置比较简单不建议采用
 * https://blog.csdn.net/u010046908/article/details/56015388
 */
public class SpringQtz extends QuartzJobBean {

    static Logger logger = LoggerFactory.getLogger(SpringQtz.class);

    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();
//        System.out.println(" SpringQtz start  执行");
//        System.out.println("SpringQtz-------"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms))+"  "+"(" + counter++ + ")");
    }


}
