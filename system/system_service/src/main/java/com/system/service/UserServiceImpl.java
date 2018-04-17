package com.system.service;

import com.system.atom.bean.User;
import com.system.atom.dao.UserMapper;
import com.system.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService
{

    @Autowired
    UserMapper userMapper;
    /**
     * 根据号码查找用户
     *
     * @param phone
     * @return
     */
    @Override
    public User findUserByPhone(String phone)
    {
        return userMapper.findUserByPhone(phone);
    }

    /**
     * 添加新用户
     *
     * @param user
     * @return
     */
    @Override
    public int addUser(User user)
    {
        return userMapper.insert(user.getName(),user.getPassword(),user.getPhone());
    }
}
