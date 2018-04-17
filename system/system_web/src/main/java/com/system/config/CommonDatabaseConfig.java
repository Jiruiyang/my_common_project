package com.system.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @description：数据源配置
 * @author：yangjr
 * @date：2018/4/17 Created by yangjirui on 2018/4/17.
 */
@Configuration
public class CommonDatabaseConfig
{
    @Autowired
    private Environment env;

    /**
     * 业务数据库
     *
     * @return
     */
    @Bean
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("common.datasource.driverClassName", "com.mysql.jdbc.Driver"));
        dataSource.setUrl(env.getProperty("common.datasource.url"));
        dataSource.setUsername(env.getProperty("common.datasource.username"));
        dataSource.setPassword(env.getProperty("common.datasource.password"));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("common.datasource.maxActive", "8")));
        dataSource.setMaxIdle(Integer.parseInt(env.getProperty("common.datasource.maxIdle", "8")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("common.datasource.minIdle", "0")));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("common.datasource.initialSize", "0")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("common.datasource.testOnReturn", "false")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("common.datasource.testWhileIdle", "false")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("common.datasource.testOnBorrow", "true")));
        dataSource.setLogAbandoned(Boolean.parseBoolean(env.getProperty("common.datasource.logAbandoned", "false")));
        dataSource.setValidationQuery(env.getProperty("common.datasource.validationQuery"));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("common.datasource.timeBetweenEvictionRunsMillis", "-1")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("common.datasource.minEvictableIdleTimeMillis", "1800000")));
        dataSource.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("common.datasource.removeAbandoned", "false")));
        return dataSource;
    }

    @Bean("namedJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate()
    {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
        return namedParameterJdbcTemplate;
    }
}
