package com.system.service.intf;

import com.system.atom.bean.param.Student;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */

public interface StudentService
{
    /**
     * 根据姓名查找学生
     *
     * @param name
     * @return
     */
    Student findStudentByName(String name);

    /**
     * 添加新同学
     *
     * @param student
     * @return
     */
    int addStudent(Student student);
}
