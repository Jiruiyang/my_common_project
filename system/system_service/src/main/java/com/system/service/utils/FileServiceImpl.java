package com.system.service.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @description：
 * @author：yangjr
 * @date：2018/10/8 Created by yangjirui on 2018/10/8.
 */
@Service("fileService")
public class FileServiceImpl implements FileService
{
    private Logger logger = Logger.getLogger(this.getClass());

    private String uploadDir;

    private void setUploadDir(String uploadDir) {
        if(!uploadDir.endsWith(File.separator.toString())){
            uploadDir = uploadDir + File.separator;
        }
        this.uploadDir = uploadDir;
    }

    public Map<String, String> upload(MultipartHttpServletRequest request, String staffId) throws IOException {
        HashMap fileMap = new HashMap();
        Iterator iter = request.getFileNames();
        while(iter.hasNext()) {
            MultipartFile file = request.getFile((String)iter.next());
            if(file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
                String uploadFilePath = upload(file, staffId);
                fileMap.put(file.getOriginalFilename(), uploadFilePath);
                logger.debug("fileMap:" + fileMap);
            }
        }

        return fileMap;
    }

    public String upload(MultipartFile file, String staffId) throws IOException {
        String filePath = null;
        if(file != null && file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(uploadDir).append(staffId).append(File.separator)
                    .append(UUID.randomUUID()).append("_").append(file
                    .getOriginalFilename().substring(file.getOriginalFilename().indexOf
                            (".")));
            filePath = stringBuffer.toString();
            FileOutputStream outputStream = new FileOutputStream(new File(filePath));
            FileCopyUtils.copy(file.getInputStream(), outputStream);
        }

        return filePath;
    }

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
            throws Exception
    {
        List<T> resultList = ExcelParser.parseExcelFile(cls, file.getInputStream(), cellKeys);
        return resultList;
    }

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
            throws Exception
    {
        Iterator iter = request.getFileNames();
        List<T> resultList = new ArrayList<T>();

        while(true) {
            MultipartFile file;
            do {
                do {
                    if(!iter.hasNext()) {
                        return resultList;
                    }
                    file = request.getFile((String)iter.next());
                } while(file == null);
            } while(StringUtils.isEmpty(file.getOriginalFilename()));

            BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()));
            resultList.addAll(ExcelParser.parseExcelFile(cls, file.getInputStream(), cellKeys));
            b.close();
        }
    }

    /**
     * 解析excel文件
     * @param request
     * @param cellKeys
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> parseExcelFile(MultipartHttpServletRequest request,
            String[] cellKeys) throws Exception
    {
        Iterator iter = request.getFileNames();
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        while(true) {
            MultipartFile file;
            do {
                do {
                    if(!iter.hasNext()) {
                        return resultList;
                    }
                    file = request.getFile((String)iter.next());
                } while(file == null);
            } while(StringUtils.isEmpty(file.getOriginalFilename()));

            BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()));
            resultList.addAll(ExcelParser.parseExcelFile(file.getInputStream(), cellKeys));
            b.close();
        }
    }

    public <T> List<T> parseFile(MultipartFile file, FileLineConverter<T> fileLineConverter) throws IOException {
        ArrayList resultList = new ArrayList();
        if(file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String value = b.readLine();
            if(!StringUtils.isEmpty(value)) {
                resultList.add(fileLineConverter.convert(value));

                while(!StringUtils.isEmpty(value)) {
                    value = b.readLine();
                    if(!StringUtils.isEmpty(value)) {
                        resultList.add(fileLineConverter.convert(value));
                    }
                }
            }

            b.close();
        }

        return resultList;
    }

    public boolean checkFileBatchLimit(MultipartHttpServletRequest request) throws IOException {
        Iterator iter = request.getFileNames();
        int count = 0;

        while(true) {
            MultipartFile file;
            do {
                do {
                    if(!iter.hasNext()) {
                        return count <= 200;
                    }

                    file = request.getFile((String)iter.next());
                } while(file == null);
            } while(StringUtils.isEmpty(file.getOriginalFilename()));

            BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String value = b.readLine();
            if(value != null) {
                while(value != null) {
                    ++count;
                    value = b.readLine();
                }
            }

            b.close();
        }
    }

    public <T> List<T> parseFile(MultipartHttpServletRequest request, FileLineConverter<T>
            fileLineConverter) throws IOException {
        Iterator iter = request.getFileNames();
        ArrayList resultList = new ArrayList();

        while(true) {
            MultipartFile file;
            do {
                do {
                    if(!iter.hasNext()) {
                        return resultList;
                    }

                    file = request.getFile((String)iter.next());
                } while(file == null);
            } while(StringUtils.isEmpty(file.getOriginalFilename()));

            BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String value = b.readLine();
            if(!StringUtils.isEmpty(value)) {
                resultList.add(fileLineConverter.convert(value));

                while(!StringUtils.isEmpty(value)) {
                    value = b.readLine();
                    if(!StringUtils.isEmpty(value)) {
                        resultList.add(fileLineConverter.convert(value));
                    }
                }
            }

            b.close();
        }
    }

}
