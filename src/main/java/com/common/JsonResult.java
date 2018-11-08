package com.common;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class JsonResult implements Serializable {

    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public JsonResult(Object data) {
        this.data = data;
    }
    public JsonResult() {
    }
    @ApiModelProperty(value = "状态码", notes = "1是成功,其他都是失败")
    private int code=HttpReturnRnums.Success.value();
    @ApiModelProperty(value = "状态码描述信息", notes = "例如失败，参数错误")
    private String msg= HttpReturnRnums.Success.desc();
    @ApiModelProperty(value = "具体的业务数据", notes = "")
    private Object data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonResult get(int code, String msg, Object data) {
        return new JsonResult(code, msg,data);

    }
    public static JsonResult get(int code, String msg) {
        return JsonResult.get(code,msg,null);
    }
    public static JsonResult get(Object data) {
        return JsonResult.get(1,"ok",data);
    }

}

