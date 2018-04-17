package com.system.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author : hangke
 * @description :〈描述〉
 * @date : 16/2/29
 */
public class BaseController
{

    /**
     * logger日志类
     */
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * debug日志
     *
     * @param msg 日志信息
     */
    protected void debug(Object msg)
    {
        logger.debug(msg);
    }

    /**
     * multipartResolver
     */
    @Autowired
    private MultipartResolver multipartResolver;

    /**
     * 转换请求参数为json格式
     *
     * @param request 请求
     * @return 请求参数
     */
    protected JSONObject getParamObject(HttpServletRequest request)
    {
        JSONObject jsonObject = getParamObjectWithFormData(request);
        String payload = getRequestPayload(request);
        if (!StringUtils.isEmpty(payload))
        {

            jsonObject.putAll(parseBodyParamPair(payload));
        }

        return jsonObject;
    }

    /**
     * 转换请求参数中的FormData参数为json格式
     *
     * @param request 请求
     * @return 请求参数
     */
    protected JSONObject getParamObjectWithFormData(HttpServletRequest request)
    {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet())
        {
            String[] values = params.get(key);
            StringBuffer value = new StringBuffer("");
            for (int i = 0; i < values.length; i++)
            {
                value.append(values[i]);
            }
            jsonObject.put(key, value.toString());
        }
        return jsonObject;
    }

    private JSONObject parseBodyParamPair(String payloadStr)
    {
        JSONObject paramJson = new JSONObject();
        if (payloadStr.indexOf("{") != 0 && payloadStr.indexOf("&") >= 0)
        {
            String[] paramPairStrArray = payloadStr.split("&");
            for (String paramStr : paramPairStrArray)
            {
                if (!StringUtils.isEmpty(paramStr) && paramStr.indexOf("=") >= 0)
                {
                    String[] paramPairStr = paramStr.split("=");
                    paramJson.put(paramPairStr[0], paramPairStr[1]);
                }
            }
        }
        else
        {
            paramJson = JSONObject.parseObject(payloadStr);
        }
        return paramJson;
    }

    /**
     * 获取request payload中的json
     *
     * @param req
     * @return
     */
    private String getRequestPayload(HttpServletRequest req)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1)
            {
                sb.append(buff, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @param request request
     * @return boolean
     */
    protected boolean isMultipartHttpServletRequest(HttpServletRequest request)
    {
        return multipartResolver.isMultipart(request);
    }

    /**
     * @param request 请求
     * @return MultipartHttpServletRequest
     */
    protected MultipartHttpServletRequest processMultipartHttpServletRequest(HttpServletRequest
                                                                                     request)
    {
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        if (multipartResolver.isMultipart(request))
        {
            multipartHttpServletRequest = multipartResolver.resolveMultipart(request);
        }
        return multipartHttpServletRequest;
    }

}
