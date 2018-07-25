package com;

import com.system.BaseApplicationTest;
import com.system.atom.bean.param.Student;
import com.system.atom.bean.system.User;
import com.system.service.intf.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
public class StudentTest extends BaseApplicationTest
{

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    @Autowired
    StudentService studentService;

    @Test
    public void findStudentByNameTest()
    {
        Student u = studentService.findStudentByName("yjr");
        if (null != u)
        {
            logger.info(u.toString());
        }
        else
        {
            logger.info("student is null");
        }
    }

    @Test
    public void addUserTest()
    {
        Student student = new Student();
        student.setName("xiao瑞");
        student.setSex("male");
        int res = studentService.addStudent(student);
        logger.info("res = " + res);
    }
}
