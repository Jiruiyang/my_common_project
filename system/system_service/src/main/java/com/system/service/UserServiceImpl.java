package com.system.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.atom.bean.system.User;
import com.system.atom.mapper.system.UserMapper;
import com.system.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查找所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll()
    {
        return userMapper.findAll();
    }

    /**
     * 分页查找
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findByPage(int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        Page<User> users = userMapper.findByPage(pageNum, pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }
}
