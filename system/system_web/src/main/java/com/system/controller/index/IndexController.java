package com.system.controller.index;

import com.system.controller.BaseController;
import com.system.error.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description：首页
 * @author：yangjr
 * @date：2018/6/14 Created by yangjirui on 2018/6/14.
 */
@Controller
public class IndexController extends BaseController
{

    @RequestMapping("/index")
    public Object hello()
    {
        return "index/index";
    }
}
