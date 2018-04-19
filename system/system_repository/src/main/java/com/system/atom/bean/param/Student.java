package com.system.atom.bean.param;

/**
 * @description：student类
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
public class Student
{
    private Integer id;
    private String name;
    private String sex;
    private String age;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }
}
