package com.system.controller;

import com.system.WebResult;
import com.system.atom.bean.param.Student;
import com.system.atom.bean.system.User;
import com.system.error.MyException;
import com.system.service.intf.StudentService;
import com.system.service.intf.UserService;
import com.system.service.utils.ExcelParser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description：项目测试
 * @author：yangjr
 * @date：2018/4/16 Created by yangjirui on 2018/4/16.
 */
//@RestController
@Controller
public class TestController extends BaseController
{
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    private final String[] headers = new String[]{
            "id","姓名","密码","手机号"
    };

    private final String[] tableColums = new String[]{
            "ID","NAME","PASSWORD","PHONE"
    };

    @RequestMapping("/hello")
    public Object hello() throws MyException
    {
        logger.info("这个是hello");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name1", "222");
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping("/json2")
    public String json() throws MyException
    {
        return "hello";
    }

    @RequestMapping("/user/findUser")
    @ResponseBody
    public Object findUser(ModelMap modelMap, HttpServletRequest request) throws MyException
    {
        User user = userService.findUserByPhone("18795997601");
        WebResult webResult = new WebResult();
        webResult.ok().setData(user);
        return webResult;
    }

    @RequestMapping("/user/findStudent")
    @ResponseBody
    public Object findStudent(ModelMap modelMap, HttpServletRequest request) throws MyException
    {
        Student student = studentService.findStudentByName("yjr");
        WebResult webResult = new WebResult();
        User user = userService.findUserByPhone("18795997600");
        webResult.ok().set("student",student).set("USER",user);
        return webResult;
    }

    @RequestMapping("/user/export")
    public void exportExcel(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) throws MyException
    {
        String fileName = "销售日报.xls";
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        response.setContentType("application/x-msdownload;charset=UTF-8");
        try
        {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(sFormat.format(new Date()).toString() + fileName, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        try
        {
            HSSFWorkbook workbook = new HSSFWorkbook();
            List<User> userList = userService.findAll();
            List<Map<String,Object>> list = new ArrayList<>();
            for (User user:userList)
            {
                Map map = new HashMap();
                map.put("ID",user.getId());
                map.put("NAME",user.getName());
                map.put("PASSWORD",user.getPassword());
                map.put("PHONE",user.getPhone());
                list.add(map);
            }
            ExcelParser.writeToExcelBySheet(list, workbook, "日报", headers, tableColums);
            ServletOutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
