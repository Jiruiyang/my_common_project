package com.system.atom.mapper.param;

import com.system.atom.bean.param.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
@Mapper
public interface StudentMapper
{
    @Select("SELECT * FROM SC_STUDENT WHERE NAME = #{name}")
    Student findStudentByName(@Param("name") String name);

    @Insert("INSERT INTO SC_STUDENT(NAME, SEX, AGE) VALUES(#{name}, #{sex}, #{age})")
    int insert(@Param("name") String name, @Param("sex") String sex, @Param("age") String age);

}
