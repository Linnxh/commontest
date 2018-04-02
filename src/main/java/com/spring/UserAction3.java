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


//@interface
@Api(value = "common3(Controller层)")
@Controller
@RequestMapping(value = "/common3")
public interface UserAction3 {


    @ApiOperation(value = "得到skuinfo", httpMethod = "POST", notes = "lxh")
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test();

}
