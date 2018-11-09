package com.spring.impl;

import com.spring.inter.HelloInter;
import com.test.bean.Sku;
import com.test.dao.SkuMapper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Path;

@Service
@Path("受苦了的减肥卡兰蒂斯")
@Component
@RequestMapping(value="/HelloImpl",name = "#了时代峻峰库里的" )
public class HelloImpl implements HelloInter {

    @Autowired
    SkuMapper skuMapper;

    @ApiOperation(value="测试-getSkuInfo  ",httpMethod="POST",notes="lxh2")
    @RequestMapping(value = "/getSkuInfo")
    @ResponseBody
    @Override
    public Sku getSkuInfo() {
        return skuMapper.selectByPrimaryKey((long) 110033073);
    }

    @ApiOperation(value="测试-getSkuInfo2",httpMethod="POST",notes="lxh")
    @RequestMapping(value = "/getSkuInfo2")
    @ResponseBody
    @Override
    public Sku getSkuInfo2() {
        return skuMapper.selectByPrimaryKey((long) 110033073);
    }
}
