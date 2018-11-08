package com.spring;

import com.spring.impl.HelloImpl;
import com.test.bean.Sku;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

@Controller
@RequestMapping(value="/common",name = "#了时代峻峰库里的" )
public class UserAction {
    /**
     * pom文件。spring，jstl、servlet、
     * spring-servlet.xml文件，<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" p:prefix="/WEB-INF/view/" p:suffix=".jsp"/></beans>
     * controller定义
     */
    @Autowired
    HelloImpl helloImpl;

    @ApiOperation(value="测试-toindex",httpMethod="POST",notes="lxh")
    @RequestMapping(value = "/toindex.do")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.addAttribute("name", "zhangsan");
        modelMap.put("name2", "zhangsan2");
        return new ModelAndView("/index", modelMap);
    }

    @ApiOperation(value="测试-tohello",httpMethod="POST",notes="lxh")
    @RequestMapping(value = "/tohello.do")
    public ModelAndView Hello(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv=new ModelAndView("/index");
        mv.addObject("name", "zhangsan");
        return mv;
    }

    @ApiOperation(value="得到skuinfo",httpMethod="POST",notes="lxh")
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Sku test(){
        return helloImpl.getSkuInfo();
    }

}
