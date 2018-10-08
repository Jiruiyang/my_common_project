package com.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description：
 * @author：yangjr
 * @date：2018/4/16 Created by yangjirui on 2018/4/16.
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.system")
public class SysApplication extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SysApplication.class);
    }



    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }
}
