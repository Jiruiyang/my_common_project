package com.system.service.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.util.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Description：Excel操作类
 * CreateAuthor：yangjr
 * CreateDate：2017/7/21
 * <p>
 * Created by yangjirui on 2017/7/21.
 */
public class ExcelParser
{
    private static final String SUFFIX = "xlsx";

    private static final String CSV = "csv";

    private final static String SPLIT_KEY = "_";
    /**
     * 每个sheet页最大数据量
     */
    private static final int MAX_NUMBER = 65535;


    /**
     * 根据Excel生成对应的对象
     *
     * @param clazz    对应的对象类型
     * @param file     上传文件
     * @param cellKeys 对应Excel的行数据,每行对应到对象的Field值
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseExcelFile(Class<T> clazz, File file, String[] cellKeys) throws Exception
    {
        List<T> objList = parseExcelFile(clazz, new FileInputStream(file), cellKeys);
        return objList;
    }

    /**
     * 根据Excel生成对应的对象
     *
     * @param clazz       对应的对象类型
     * @param inputStream 上传文件流
     * @param cellKeys    对应Excel的行数据,每行对应到对象的Field值
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseExcelFile(Class<T> clazz, InputStream inputStream, String[] cellKeys) throws Exception
    {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        if (null == inputStream)
        {
            throw new RuntimeException("Empty file!");
        }
        if (null == cellKeys || cellKeys.length == 0)
        {
            throw new RuntimeException("Cellkeys can't be null");
        }
        Workbook wb = null;
        try
        {
            // 构造Workbook（工作薄）对
            wb = WorkbookFactory.create(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        List<T> objList = new ArrayList<T>();
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++)
        {
            Row row = sheet.getRow(i);
            if (row != null)
            {
                int lastCellNum = Math.min(row.getLastCellNum(), cellKeys.length);
                Cell cell = null;
                T object = clazz.newInstance();
                for (int j = 0; j < lastCellNum; j++)
                {
                    cell = row.getCell(j);
                    if (cell != null)
                    {
                        Field field = ReflectionUtils.findField(clazz, convertSlashKey(cellKeys[j].toLowerCase()));
                        if (field != null)
                        {
                            ReflectionUtils.makeAccessible(field);
                            field.set(object, typeConverter.convertIfNecessary(getCellValue(cell), field.getType()));
                        }
                    }

                }
                objList.add(object);
            }
        }
        return objList;
    }

    /**
     * 根据Excel生成对应的对象
     *
     * @param inputStream 上传文件流
     * @param cellKeys    对应Excel的行数据,每行对应到对象的Field值
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> parseExcelFile(InputStream inputStream, String[] cellKeys) throws Exception
    {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        if (null == inputStream)
        {
            throw new RuntimeException("Empty file!");
        }
        if (null == cellKeys || cellKeys.length == 0)
        {
            throw new RuntimeException("Cellkeys can't be null");
        }
        Workbook wb = null;
        try
        {
            // 构造Workbook（工作薄）对
            wb = WorkbookFactory.create(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++)
        {
            Row row = sheet.getRow(i);
            if (row != null)
            {
                int lastCellNum = Math.min(row.getLastCellNum(), cellKeys.length);
                Cell cell = null;
                Map<String, Object> map = new HashMap<String, Object>();
                for (int j = 0; j < lastCellNum; j++)
                {
                    cell = row.getCell(j);
                    if (cell != null)
                    {
                        map.put(cellKeys[j], getCellValue(cell));
                    }
                }
                objList.add(map);
            }
        }
        return objList;
    }

    /**
     * 去除 a_demo 转换为aDemo
     *
     * @param key
     * @return
     */
    public static String convertSlashKey(String key)
    {
        int index = key.indexOf(SPLIT_KEY);
        String result;
        if (index > 0)
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(key.substring(0, index)).append(String.valueOf(key.charAt(index + 1)).toUpperCase()).append(key.substring(index + 2));
            key = stringBuffer.toString();
            if (key.indexOf(SPLIT_KEY) > 0)
            {
                result = convertSlashKey(key);
            }
            else
            {
                result = key;
            }
        }
        else
        {
            result = key;
        }

        return result;
    }

    /**
     * 写内容到excel中
     *
     * @throws IOException
     */
    public static void writeExcelFile(String filePath, List<?> list, String[] headers, IWriteWorkBookCell writeWorkBookCell)
    {
        FileOutputStream out = null;
        try
        {
//            String path = ExcelParser.class.getClass().getResource("/").getPath();
//            filePath = path + (filePath.startsWith("/") ? filePath.substring(1) : filePath);
//            System.out.println("filePath:" + filePath);
            Workbook book = getExcelWorkbook(filePath);
            Sheet sheet = book.createSheet("sheet1");

            boolean result = writeToExcel(list, sheet, headers, writeWorkBookCell);
            if (result)
            {
                out = new FileOutputStream(filePath);
                book.write(out);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取excel的Workbook
     *
     * @throws IOException
     */
    public static Workbook getExcelWorkbook(String filePath) throws IOException
    {
        Workbook book = null;
        File file = null;
        FileInputStream fis = null;
        try
        {
            file = new File(filePath);
            if (!file.exists())
            {
                file.createNewFile();
            }
            if (file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(".")).endsWith(SUFFIX))
            {
                book = new XSSFWorkbook();
            }
            else
            {
                book = new HSSFWorkbook();
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            if (fis != null)
            {
                fis.close();
            }
        }
        return book;
    }

    /**
     * 将传入的内容写入到excel中sheet里
     *
     * @param list
     */
    public static boolean writeToExcel(List<?> list, Sheet sheet, String[] headers, IWriteWorkBookCell writeWorkBookCell)
    {
        int startRow = 1;
        boolean result = false;
        try
        {
            //新建第一行头
            Row headRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++)
            {
                Cell cell = headRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            //处理内容
            for (int i = 0; i < list.size(); i++)
            {
                Object obj = list.get(i);
                Row row = sheet.getRow(startRow);
                if (row == null)
                {
                    row = sheet.createRow(startRow);
                }
                startRow++;
                int colNum = headers.length;
                for (int j = 0; j < colNum; j++)
                {
                    Cell cell = row.getCell(j);
                    if (cell == null)
                    {
                        cell = row.createCell(j);
                    }
                    writeWorkBookCell.setCellValue(cell, obj, j);
                }
            }
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 将传入的内容写入到excel中sheet里
     *
     * @param list
     * @param workbook
     * @param sheetName
     * @param headers
     * @param fields
     */
    public static void writeToExcelBySheet(List<Map<String, Object>> list, HSSFWorkbook workbook, String sheetName, String[] headers, String[] fields)
    {

        if (list.size() > MAX_NUMBER)
        {
            long sheetNumber = 0;
            if (list.size() % MAX_NUMBER == 0)
            {
                sheetNumber = list.size() / MAX_NUMBER;
            }
            else
            {
                sheetNumber = list.size() / MAX_NUMBER + 1;
            }
            for (int i = 0; i < sheetNumber; i++)
            {
                long size = (i + 1) * MAX_NUMBER > list.size() ? list.size() : (i + 1) * MAX_NUMBER;
                List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
                for (int j = i * MAX_NUMBER; j < size; j++)
                {
                    sheetList.add(list.get(j));
                }
                HSSFSheet sheet = workbook.createSheet(sheetName + "_" + (i + 1));
                ExcelParser.writeToExcelBySheet(sheetList, sheet, headers, fields);
            }
        }
        else
        {
            HSSFSheet sheet = workbook.createSheet(sheetName);
            ExcelParser.writeToExcelBySheet(list, sheet, headers, fields);
        }

    }

    /**
     * 将传入的内容写入到excel中sheet里
     *
     * @param list
     * @param filePath
     * @param sheetName
     * @param headers
     * @param fields
     */
    public static void writeToExcelBySheet(List<Map<String, Object>> list, String filePath, String sheetName, String[] headers, String[] fields)
    {

        Workbook workbook = null;
        Sheet sheet = null;

        if (filePath.endsWith(SUFFIX))
        {
            workbook = new XSSFWorkbook();
        }
        else
        {
            workbook = new HSSFWorkbook();
        }

        if (list.size() > MAX_NUMBER)
        {
            long sheetNumber = 0;
            if (list.size() % MAX_NUMBER == 0)
            {
                sheetNumber = list.size() / MAX_NUMBER;
            }
            else
            {
                sheetNumber = list.size() / MAX_NUMBER + 1;
            }
            for (int i = 0; i < sheetNumber; i++)
            {
                long size = (i + 1) * MAX_NUMBER > list.size() ? list.size() : (i + 1) * MAX_NUMBER;
                List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
                for (int j = i * MAX_NUMBER; j < size; j++)
                {
                    sheetList.add(list.get(j));
                }
                sheet = workbook.createSheet(sheetName + "_" + (i + 1));
                ExcelParser.writeToExcelBySheet(sheetList, sheet, headers, fields);
            }
        }
        else
        {
            sheet = workbook.createSheet(sheetName);
            ExcelParser.writeToExcelBySheet(list, sheet, headers, fields);
        }

        FileOutputStream fout = null;
        try
        {
            fout = new FileOutputStream(filePath);
            workbook.write(fout);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 将传入的内容写入到excel中sheet里
     *
     * @param list    list
     * @param sheet   sheet
     * @param headers headers
     * @param fields  fields
     * @return boolean
     */
    public static boolean writeToExcelBySheet(List<?> list, Sheet sheet, String[] headers, String[] fields)
    {
        if (headers.length > fields.length)
        {
            throw new RuntimeException("headers must be must be greater than the length of fields");
        }
        int startRow = 1;
        boolean result = false;

        //新建第一行头
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            Cell cell = headRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        String fieldKey = null;
        Object fieldValue = null;

        //处理内容
        for (int i = 0; i < list.size(); i++)
        {
            Object obj = list.get(i);
            Row row = sheet.getRow(startRow);
            if (row == null)
            {
                row = sheet.createRow(startRow);
            }
            startRow++;
            int colNum = headers.length;
            for (int j = 0; j < colNum; j++)
            {
                fieldKey = fields[j];
                Cell cell = row.getCell(j);
                if (cell == null)
                {
                    cell = row.createCell(j);
                }
                fieldValue = ((Map) obj).get(fieldKey);
                cell.setCellValue(fieldValue == null ? "" : String.valueOf(fieldValue));
            }
        }
        result = true;
        return result;
    }

    /**
     * 根据excel单元格类型获取excel单元格值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell)
    {
        String cellvalue = "";
        if (cell != null)
        {
            // 判断当前Cell的Type
            switch (cell.getCellType())
            {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                {
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58)
                    {    //excel中的时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil.getJavaDate(value);
                        cellvalue = sdf.format(date);
                    }
                    // 判断当前的cell是否为Date
                    else if (HSSFDateUtil.isCellDateFormatted(cell))
                    {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                        // 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式
                        Date date = cell.getDateCellValue();
                        cellvalue = DateFormatUtils.format(date, "yyyy-MM-dd");
                    }
                    else
                    { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());

                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''").trim();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellvalue = null;
                    break;
                // 默认的Cell值
                default:
                {
                    cellvalue = "";
                }
            }
        }
        else
        {
            cellvalue = "";
        }
        return cellvalue;
    }
}

