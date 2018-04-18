package com.system;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description：Web通用返回工具类
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
public class WebResult extends JSONObject
{
    /**
     * 成功响应编码
     */
    public static final String SUCCESS_CODE = "0000";

    /**
     * 其它错误编码
     */
    public static final String OTHER_ERROR_CODE = "8888";

    /**
     * 响应码Key
     */
    public static final String RSP_CODE_KEY = "RESP_CODE";

    /**
     * 响应描述Key
     */
    public static final String RSP_DESC_KEY = "RESP_DESC";

    /**
     * 数据集Key
     */
    public static final String DATA_KEY = "DATA";


    /**
     * 构造函数
     */
    public WebResult()
    {
        super();
    }

    /**
     * 获取错误编码
     *
     * @return 错误编码
     */
    public String getErrorCode()
    {
        return super.get(RSP_CODE_KEY).toString();
    }

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    public String getMessage()
    {
        return super.get(RSP_DESC_KEY).toString();
    }

    /**
     * 设置错误编码
     *
     * @param error 错误编码
     */
    public WebResult setError(String error)
    {
        super.put(RSP_CODE_KEY, error);
        return this;
    }

    /**
     * 设置错误描述
     *
     * @param message 错误描述
     */
    public WebResult setMessage(String message)
    {
        super.put(RSP_DESC_KEY, message);
        return this;
    }

    /**
     * 成功响应
     *
     * @return WebResult
     */
    public WebResult ok()
    {
        super.put(RSP_CODE_KEY, SUCCESS_CODE);
        return this;
    }

    /**
     * 成功响应
     *
     * @return WebResult
     */
    public WebResult ok(String message)
    {
        super.put(RSP_CODE_KEY, SUCCESS_CODE);
        super.put(RSP_DESC_KEY, message);
        return this;
    }

    /**
     * 设置数据集
     *
     * @param data
     * @return
     */
    public WebResult setData(Object data)
    {
        super.put(DATA_KEY, data);
        return this;
    }

    /**
     * * 失败响应
     *
     * @param errorCode 错误响应编码
     * @param errorMsg  错误响应信息
     * @return WebResult
     */
    public WebResult fail(String errorCode, String errorMsg)
    {
        if (StringUtils.isEmpty(errorCode))
        {
            super.put(RSP_CODE_KEY, OTHER_ERROR_CODE);
            super.put(RSP_DESC_KEY, "unknown error");
        }
        else
        {
            super.put(RSP_CODE_KEY, errorCode);
            super.put(RSP_DESC_KEY, errorMsg);
        }
        return this;
    }

    /**
     * 失败响应
     *
     * @return WebResult
     */
    public WebResult fail()
    {
        super.put(RSP_CODE_KEY, OTHER_ERROR_CODE);
        super.put(RSP_DESC_KEY, "unknown error");
        return this;
    }

    /**
     * 失败响应
     *
     * @return WebResult
     */
    public WebResult fail(String message)
    {
        super.put(RSP_CODE_KEY, OTHER_ERROR_CODE);
        super.put(RSP_DESC_KEY, message);
        return this;
    }

    /**
     * 设置 key-value
     *
     * @param key   key
     * @param value value
     * @return WebResult
     */
    public WebResult set(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * 返回Map对象,Comframe比较搓,适配修改
     *
     * @return
     */
    public Map deepClone()
    {
        Map cloneMap = new HashMap();
        Iterator<Entry<String,Object>> iterator = this.entrySet().iterator();
        while (iterator.hasNext())
        {
            Entry entry = iterator.next();
            cloneMap.put(entry.getKey(),entry.getValue());
        }
        return cloneMap;
    }

    //接口中返回信息结点
    public final static String INTERFACE_RESP_INFO = "RSP_INFO";

    /**
     * 是否是成功
     *
     * @return
     */
    public boolean isSuccess()
    {
        if (super.getString(RSP_CODE_KEY) != null && super.getString(RSP_CODE_KEY).equals(SUCCESS_CODE))
        {
            return true;
        }
        return false;
    }

    /**
     * 设置接口中RSP_INFO结点
     *
     * @param respInfo
     */
    public void putRespInfo(Object respInfo)
    {
        super.put(INTERFACE_RESP_INFO, respInfo);
    }

}
