package com.system.controller;

import com.system.WebResult;
import com.system.atom.bean.User;
import com.system.error.MyException;
import com.system.service.intf.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @description：项目测试
 * @author：yangjr
 * @date：2018/4/16 Created by yangjirui on 2018/4/16.
 */
//@RestController
@Controller
public class TestController extends BaseController
{
    @Autowired
    UserService userService;

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

    @RequestMapping("/user/findUser")
    @ResponseBody
    public Object findUser(ModelMap modelMap, HttpServletRequest request) throws MyException
    {
        User user = userService.findUserByPhone("18795997581");
        WebResult webResult = new WebResult();
        webResult.ok().setData(user);
        return webResult;
    }

}
