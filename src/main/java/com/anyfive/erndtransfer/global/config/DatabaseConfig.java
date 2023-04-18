package com.anyfive.erndtransfer.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages = { "com.anyfive.erndtransfer.**.mapper" }, annotationClass = Mapper.class)
public class DatabaseConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory() throws Exception {
    // SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    // Resource configLocation = new PathMatchingResourcePatternResolver()
    // .getResource("classpath:config/mybatis-config.xml");
    // factoryBean.setDataSource(dataSource());
    // factoryBean.setMapperLocations(context.getResources("classpath:mapper/*Mapper.xml"));
    // // factoryBean.setConfiguration(mybatisConfig());
    // factoryBean.setConfigLocation(configLocation);
    // return factoryBean.getObject();
    // }
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new RefreshableSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setVfs(SpringBootVFS.class);
        Resource configLocation = new PathMatchingResourcePatternResolver()
                .getResource("classpath:config/mybatis-config.xml");
        factoryBean.setConfigLocation(configLocation);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = resolver.getResources("classpath*:mapper/*Mapper.xml");
        factoryBean.setMapperLocations(resource);
        ((RefreshableSqlSessionFactoryBean) factoryBean).setInterval(1000);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }

}