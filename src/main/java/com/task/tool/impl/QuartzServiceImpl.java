package com.task.tool.impl;

import com.common.JsonResult;
import com.task.tool.bean.QuartzServiceModel;
import com.task.tool.inter.QuartzServiceInter;
import com.test.dao.QuartzServiceMapper;
import com.test.dao.SkuMapper;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.tools.scalap.scalax.util.StringUtil;

import javax.ws.rs.Path;
import java.util.List;


@Service
@Component
@RequestMapping(value = "/quartzserviceimpl", name = "定时服务接口")
public class QuartzServiceImpl implements QuartzServiceInter {

    @Autowired
    QuartzServiceMapper mapper;

    @ApiOperation(value = "查询所有列表", httpMethod = "POST", notes = "lxh2")
    @RequestMapping(value = "/querystartlist")
    @ResponseBody
    @Override
    public List<QuartzServiceModel> queryStartList() {
        return mapper.queryStartList();
    }


    @ApiOperation(value = "插入", httpMethod = "POST", notes = "lxh2")
    @RequestMapping(value = "/insert")
    @ResponseBody
    @Override
    public JsonResult insert(@RequestBody QuartzServiceModel record) {
        if (record == null || StringUtils.isEmpty(record.getName())) {
            return new JsonResult(-1, "参数异常");
        }
        int insert = mapper.insert(record);
        return new JsonResult(insert > 1 ? "成功" : "失败");
    }

    @Override
    public int update(QuartzServiceModel record) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    @Override
    public List<QuartzServiceModel> check(QuartzServiceModel record) {
        return null;
    }
}
