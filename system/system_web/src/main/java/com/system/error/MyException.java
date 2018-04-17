package com.system.error;

/**
 * @description：自定义异常
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
public class MyException extends Exception
{
    public MyException(String message)
    {
        super(message);
    }
}
