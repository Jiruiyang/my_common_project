package com.system.service.utils;


import org.apache.poi.ss.usermodel.Cell;

/**
 * Description：Excel使用接口
 * CreateAuthor：yangjr
 * CreateDate：2017/7/21
 * <p>
 * Created by yangjirui on 2017/7/21.
 */
public interface IWriteWorkBookCell
{
    /**
     * 处理excel行记录
     *
     * @param cell cell
     * @param obj obj
     * @param index index
     * @return boolean
     */
    boolean setCellValue(Cell cell, Object obj, int index);

}
