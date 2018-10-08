package com.system.atom.mapper.param;

import com.system.atom.bean.param.Student;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
@Mapper
public interface StudentMapper
{
    Student findStudentByName( String name);

    List<Student> findStudentListByName(List nameList);

    int insert(String name, String sex, String age);

    int selectMaxAge();

}
