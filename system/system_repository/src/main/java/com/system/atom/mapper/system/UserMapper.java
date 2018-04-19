package com.system.atom.mapper.system;

import com.github.pagehelper.Page;
import com.system.atom.bean.system.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @description：UserMapper
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
@Mapper
public interface UserMapper
{
    @Select("SELECT * FROM SC_USER WHERE PHONE = #{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Insert("INSERT INTO SC_USER(NAME, PASSWORD, PHONE) VALUES(#{name}, #{password}, #{phone})")
    int insert(@Param("name") String name, @Param("password") String password, @Param("phone") String phone);

    @Select("SELECT * FROM SC_USER")
    List<User> findAll();

    @Select("SELECT * FROM SC_USER")
    Page<User> findByPage(int pageNum,int pageSize);

}
