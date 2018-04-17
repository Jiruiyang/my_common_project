package com.system.controller;

import com.system.error.MyException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description：项目测试
 * @author：yangjr
 * @date：2018/4/16 Created by yangjirui on 2018/4/16.
 */
//@RestController
@Controller
public class TestController extends BaseController
{
    @RequestMapping("/hello")
    public Object hello() throws MyException
    {
        logger.info("这个是hello");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "222");
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping("/json2")
    public String json() throws MyException
    {
        return "hello";
    }

}
