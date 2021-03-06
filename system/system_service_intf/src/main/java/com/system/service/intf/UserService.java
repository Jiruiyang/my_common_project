package com.system.service.intf;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.system.atom.bean.system.User;

import java.util.List;

/**
 * @description：User类接口
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
public interface UserService
{
    /**
     * 根据号码查找用户
     *
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * 添加新用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 查找所有用户
     *
     * @return
     */
    List<User> findAll();

    /**
     * 分页查找
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findByPage(int pageNum, int pageSize);
}
