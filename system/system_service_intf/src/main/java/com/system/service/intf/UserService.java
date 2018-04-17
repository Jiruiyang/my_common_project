package com.system.service.intf;

import com.system.atom.bean.User;

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
}
