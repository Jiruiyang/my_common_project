package com.system;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SysApplication.class)
@WebAppConfiguration
public class BaseApplicationTest
{

    @Autowired
    private WebApplicationContext context;

    protected Logger logger = Logger.getLogger(this.getClass());

    protected MockMvc mvc;

    @Before
    public void setUp() throws Exception
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    public ResultActions performAction(String action, String jsonStr)throws Exception
    {
        return mvc.perform(MockMvcRequestBuilders.get(action)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    public ResultActions postForm(String action,Map param)throws Exception
    {
        //设置form参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        if (null != param && !param.isEmpty())
        {
            Iterator<Map.Entry<String, String>> formDataIterator = param.entrySet().iterator();
            while (formDataIterator.hasNext())
            {
                Map.Entry<String, String> entry = formDataIterator.next();
                map.add(entry.getKey(), entry.getValue());
            }
        }

        return mvc.perform(MockMvcRequestBuilders.post(action)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(map)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}