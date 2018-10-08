package com.system.service.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description：
 * @author：yangjr
 * @date：2018/10/8 Created by yangjirui on 2018/10/8.
 */

public interface FileService
{
    Map<String, String> upload(MultipartHttpServletRequest var1, String var2) throws IOException;

    boolean checkFileBatchLimit(MultipartHttpServletRequest var1) throws IOException;

    /**
     * 解析excel文件
     * @param request
     * @param cls
     * @param cellKeys
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> parseExcelFile(MultipartHttpServletRequest request, Class<T> cls, String[] cellKeys)
            throws Exception;
    /**
     * 解析excel文件
     * @param file
     * @param cls
     * @param cellKeys
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> parseExcelFile(MultipartFile file, Class<T> cls, String[] cellKeys)
            throws Exception;

    /**
     * 解析excel文件
     * @param request
     * @param cellKeys
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> parseExcelFile(MultipartHttpServletRequest request,
            String[] cellKeys) throws Exception;

    <T> List<T> parseFile(MultipartHttpServletRequest var1, FileLineConverter<T> var2) throws IOException;

    <T> List<T> parseFile(MultipartFile var1, FileLineConverter<T> var2) throws IOException;

    String upload(MultipartFile var1, String var2) throws IOException;

}
