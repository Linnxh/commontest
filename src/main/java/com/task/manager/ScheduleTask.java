package com.task.manager;

import java.io.Serializable;
public class ScheduleTask implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -6696079243640708701L;

    private String id;//任务Id
    private String name;//任务名
    private String group;//任务所在类全名称
    private String trigger;//任务执行的方法名
    private String expression;//任务频率 和cron语法保持一致
    private Object[] param;//执行任务方法的参数
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public String getTrigger() {
        return trigger;
    }
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public Object[] getParam() {
        return param;
    }
    public void setParam(Object[] param) {
        this.param = param;
    }
}
