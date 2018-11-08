package com.task.tool.controller;

import com.task.tool.bean.QuartzServiceModel;
import com.task.tool.impl.QuartzServiceImpl;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 定时服务后台配置的管理界面
 */
@Controller
@RequestMapping(value = "/quartzservicecontroller", name = "定时服务")
public class QuartzToolController {

    @Autowired
    QuartzServiceImpl quartzServiceImpl;

    @ApiOperation(value = "定时服务后台管理界面", notes = "lxh")
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.addAttribute("name", "zhangsan");
        modelMap.put("name2", "zhangsan2");
        List<QuartzServiceModel> list = quartzServiceImpl.queryStartList();
        modelMap.put("list",list);
        modelMap.addAttribute("list2", list);
        return new ModelAndView("/task/index", modelMap);
    }


}
