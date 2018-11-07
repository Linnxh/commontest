package com.task.manager;



import java.util.List;

import org.quartz.Trigger;

import com.task.manager.ScheduleTask;

public interface TaskService {
    /**
     * 获取所有的定时任务
     * @return
     */
    public List<ScheduleTask> getAllTask();
    /**
     * 获取触发器
     * @param trigger 触发器名称(这里相当于方法名)
     * @param group 任务组名称(这里相当于job所在的类名称)
     * @return
     */
    public Trigger getTrigger(String trigger,String group);
    /**
     * 添加一个新的定时任务
     * @param task
     * @return
     */
    public ScheduleTask addTask(ScheduleTask task);
    /**
     * 重启定时任务
     * @param task
     * @return
     */
    public ScheduleTask reStartTask(ScheduleTask task);
    /**
     * 删除指定定时任务
     * @param task
     * @return
     */
    public ScheduleTask deleteTask(ScheduleTask task);
    /**
     * 暂停任务
     * @param task
     * @return
     */
    public ScheduleTask pauseTask(ScheduleTask task);
    /**
     * 恢复任务
     * @param task
     * @return
     */
    public ScheduleTask resumeTask(ScheduleTask task);
    /**
     * 批量删除定时任务
     */
    public void deleteTasks(List<ScheduleTask> scheduleTasks);
    /**
     * 暂停所有的定时任务
     */
    public void pauseAllTask();

}
