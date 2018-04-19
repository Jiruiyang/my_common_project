package com;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.system.BaseApplicationTest;
import com.system.atom.bean.system.User;
import com.system.service.intf.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
public class UserTest extends BaseApplicationTest
{
    @Autowired
    private UserService userService;

    @Test
    public void findUserByPhoneTest()
    {
//        User u = userService.findUserByPhone("12345678910");
        User u = userService.findUserByPhone("18795997581");
        if (null != u)
        {
            logger.info(u.toString());
        }
        else
        {
            logger.info("user is null");
        }
    }

    @Test
    public void addUserTest()
    {
        for (int i = 0; i < 10; i++)
        {
            User user = new User();
            user.setName("小明" + i);
            user.setPassword("123456");
            user.setPhone("1879599760" + i);
            int res = userService.addUser(user);
            logger.info("res = " + res);
        }
    }

    @Test
    public void findAlltest()
    {
        logger.info(userService.findAll());
    }

    @Test
    public void findByPageTest()
    {
        PageInfo<User> pageInfo = userService.findByPage(3,10);
        logger.info(JSON.toJSONString(pageInfo.getList()));
    }
}
