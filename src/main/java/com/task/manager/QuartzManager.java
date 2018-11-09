package com.task.manager;

import com.alibaba.fastjson.JSON;
import com.task.business.HttpUtil;
import com.task.tool.bean.QuartzServiceModel;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuartzManager {


    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    public static final String ParamMapKey = "QuartzServiceModel";

    /**
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cron             时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     */
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass, String cron, HashMap<String, Object> paramMap) {
        try {


            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            if (paramMap != null && paramMap.size() > 0) {
                jobDetail.getJobDataMap().putAll(paramMap);
            }
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();


            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);


            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     * @Description: 修改一个任务的触发时间
     */
    public static void modifyJobTime(String jobName,
                                     String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }


            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */


                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @Description: 移除一个任务
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();


            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);


            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isRefreshing = false;

    public static void init(final Class cls, final String appName) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (isRefreshing) {
                    return;
                }
                isRefreshing = true;
                System.out.println("每隔" + 5000 + "秒发送" + "post请求");
                try {
                    // TODO: 2018/11/9 lxh 查询定时服务的列表页 （tool管理后添加的）
                    String json = HttpUtil.sendPost("http://192.168.3.197:8090/commontest/quartzserviceimpl/querystartlist");
                    System.out.println(json);
                    List<QuartzServiceModel> list = JSON.parseArray(json, QuartzServiceModel.class);
                    for (QuartzServiceModel q : list) {
                        if (checkModel(q)) {
                            HashMap<String, Object> paramMap = new HashMap<String, Object>();
                            paramMap.put(ParamMapKey, q);
                            addJob(q.getName(), "jobWork_lxh3", "triggerName_lxh3", "triggerGroupName_lxh3", cls, q.getExecTime(), paramMap);
                        }
                    }
                } catch (Exception e) {
                }
                isRefreshing = false;
            }
        }, 5000, 5000);
    }

    public static boolean checkModel(QuartzServiceModel quartz) {

        if (quartz == null || StringUtils.isEmpty(quartz.getName()) || StringUtils.isEmpty(quartz.getExecTime())) {
            return false;
        }
        if (quartz == null || StringUtils.isEmpty(quartz.getName()) || StringUtils.isEmpty(quartz.getExecTime())) {
            return false;
        }
        return true;
    }
}