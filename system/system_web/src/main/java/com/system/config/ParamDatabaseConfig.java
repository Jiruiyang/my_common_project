package com.system.config;

import com.github.pagehelper.PageHelper;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description：param库数据源
 * @author：yangjr
 * @date：2018/4/18 Created by yangjirui on 2018/4/18.
 */
@Configuration
@MapperScan(basePackages = "com.system.atom.mapper.param", sqlSessionTemplateRef  = "paramSqlSessionTemplate")
public class ParamDatabaseConfig
{
    @Autowired
    private Environment env;

    /**
     * 业务数据库
     *
     * @return
     */
    @Bean(name = "paramData")
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("param.datasource.driverClassName", "com.mysql.jdbc.Driver"));
        dataSource.setUrl(env.getProperty("param.datasource.url"));
        dataSource.setUsername(env.getProperty("param.datasource.username"));
        dataSource.setPassword(env.getProperty("param.datasource.password"));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("param.datasource.maxActive", "8")));
        dataSource.setMaxIdle(Integer.parseInt(env.getProperty("param.datasource.maxIdle", "8")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("param.datasource.minIdle", "0")));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("param.datasource.initialSize", "0")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("param.datasource.testOnReturn", "false")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("param.datasource.testWhileIdle", "false")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("param.datasource.testOnBorrow", "true")));
        dataSource.setLogAbandoned(Boolean.parseBoolean(env.getProperty("param.datasource.logAbandoned", "false")));
        dataSource.setValidationQuery(env.getProperty("param.datasource.validationQuery"));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("param.datasource.timeBetweenEvictionRunsMillis", "-1")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("param.datasource.minEvictableIdleTimeMillis", "1800000")));
        dataSource.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("param.datasource.removeAbandoned", "false")));
        return dataSource;
    }

    @Bean(name = "paramSqlSessionFactory")
    public SqlSessionFactory sentinelSqlSessionFactory(@Qualifier("paramData") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageHelper});
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("param.mybatis.mapperLocations")));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "paramTransactionManager")
    public DataSourceTransactionManager sentinelTransactionManager(@Qualifier("paramData") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "paramSqlSessionTemplate")
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("paramSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
