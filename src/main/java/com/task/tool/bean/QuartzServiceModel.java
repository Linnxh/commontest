package com.task.tool.bean;

import java.io.Serializable;
import java.util.Date;

public class QuartzServiceModel implements Serializable {
    private int id;
    private String reqUrl;
    private String name;
    private String execTime;
    private int isStart;
    private String remark;
    private Date createTime;
    private String createName;

    private String param; //参数
    private int reqMethod; //请求方式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(int reqMethod) {
        this.reqMethod = reqMethod;
    }
}
