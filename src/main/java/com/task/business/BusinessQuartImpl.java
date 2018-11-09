package com.task.business;

import com.common.JsonResult;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@Component
@RequestMapping(value = "/businessquartimpl", name = "定时服务向外暴露的地方")
public class BusinessQuartImpl implements BusinessQuartInter {

    @ApiOperation(value = "启动活动", httpMethod = "POST", notes = "lxh2")
    @RequestMapping(value = "/startactivity")
    @ResponseBody
    @Override
    public JsonResult startActivity() {
        return QuartzServiceHelper.doRun("activityTaskService");
    }

}
