package com.system.controller.login;

import com.system.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description：登录页面
 * @author：yangjr
 * @date：2018/6/14 Created by yangjirui on 2018/6/14.
 */
@Controller
public class LoginController extends BaseController
{
    @RequestMapping("/login")
    public Object login()
    {
        return "login/login";
    }
}
