package com.system.service;

import com.system.atom.bean.param.Student;
import com.system.atom.mapper.param.StudentMapper;
import com.system.service.intf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService
{
    @Autowired
    StudentMapper studentMapper;


    /**
     * 根据姓名查找学生
     *
     * @param name
     * @return
     */
    @Override
    public Student findStudentByName(String name)
    {
        return studentMapper.findStudentByName(name);
    }

    /**
     * 添加新同学
     *
     * @param student
     * @return
     */
    @Override
    public int addStudent(Student student)
    {
        return studentMapper.insert(student.getName(), student.getSex(), student.getAge());
    }
}
