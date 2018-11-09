package com.task.manager;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private ConcurrentHashMap<String, ScheduleTask> allTask = new ConcurrentHashMap<String, ScheduleTask>();

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public List<ScheduleTask> getAllTask() {
        List<ScheduleTask> list = new ArrayList();
        list.addAll(allTask.values());
        return list;
    }

    public CronTrigger getTrigger(String trigger, String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        CronTrigger cronTrigger = null;
        try {
            cronTrigger = (CronTrigger) scheduler.getTrigger(new TriggerKey(
                    trigger, group));
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cronTrigger;
    }

    public ScheduleTask addTask(ScheduleTask task) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class)
                    .withIdentity(task.getName(), task.getGroup()).build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(task.getTrigger(), task.getGroup())
                    .startNow()
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(task
                                    .getExpression())).build();
            System.out.println(trigger.getKey());
            Class<?> classzz = Class.forName(task.getGroup());
            Class<?>[] c = new Class<?>[task.getParam().length];
            for (int i = 0; i < task.getParam().length; i++) {
                c[i] = task.getParam()[i].getClass();
            }
            Method method = classzz.getMethod(task.getTrigger(), c);
            JobDataMap jobDataMap = trigger.getJobDataMap();
            jobDataMap.put(MyJob2.JOB_NAME, task.getName());
            jobDataMap.put(MyJob2.JOB_GROUP, classzz);
            jobDataMap.put(MyJob2.JOB_TRIGGER, method);
            jobDataMap.put(MyJob2.JOB_TRIGGER_PARAM, task.getParam());
            scheduler.scheduleJob(jobDetail, trigger);
            if (scheduler.isStarted()) {
                scheduler.start();
            }
            if (!allTask.containsKey(task.getId())) {
                allTask.put(task.getId(), task);
            }
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return task;
    }

    public ScheduleTask reStartTask(ScheduleTask task) {
        try {
            CronTrigger cronTrigger = getTrigger(task.getTrigger(),
                    task.getGroup());
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            cronTrigger = cronTrigger
                    .getTriggerBuilder()
                    .withIdentity(
                            new TriggerKey(task.getTrigger(), task.getGroup()))
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(task
                                    .getExpression())).build();

            // 按新的trigger重新设置job执行

            scheduler.rescheduleJob(
                    new TriggerKey(task.getTrigger(), task.getGroup()),
                    cronTrigger);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 下方可以做一些更新数据库中任务的操作

        return task;
    }

    public ScheduleTask deleteTask(ScheduleTask task) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(task.getName(), task.getGroup());
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {

            e.printStackTrace();
        }
        return task;
    }

    public ScheduleTask pauseTask(ScheduleTask task) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(task.getName(), task.getGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return task;
    }

    public ScheduleTask resumeTask(ScheduleTask task) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(task.getName(), task.getGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return task;
    }

    public void deleteTasks(List<ScheduleTask> scheduleTasks) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            List<JobKey> jobKeys = new ArrayList<JobKey>();
            JobKey jobKey;
            for (ScheduleTask scheduleTask : scheduleTasks) {
                jobKey = new JobKey(scheduleTask.getName(),
                        scheduleTask.getGroup());
                jobKeys.add(jobKey);
            }
            scheduler.deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void pauseAllTask() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
