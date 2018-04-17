package com;

import com.system.BaseApplicationTest;
import com.system.atom.bean.User;
import com.system.atom.dao.UserMapper;
import com.system.service.intf.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        User user = new User();
        user.setName("杨济瑞");
        user.setPassword("123456");
        user.setPhone("18795997581");
        int res = userService.addUser(user);
        logger.info("res = " + res);
    }
}
