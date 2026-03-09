package com.study.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.study.service") //扫描com.study.service包及其子包中的组件（如@Service注解的类），以便Spring能够自动检测并注册这些组件为Bean
@MapperScan("com.study.mapper") //扫描com.study.mapper包及其子包中的MyBatis Mapper接口（如UserMapper），以便MyBatis能够自动生成这些接口的实现类并注册为Bean
public class MainConfiguration {

    @Bean
    public DataSource dataSource() {
        return new PooledDataSource("com.mysql.cj.jdbc.Driver",     //数据库驱动类，MySQL 8.0及以上版本使用com.mysql.cj.jdbc.Driver
                "jdbc:mysql://localhost:3306/study", "root", "root");
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();       //创建SqlSessionFactoryBean实例
        bean.setDataSource(dataSource);     //设置数据源，Spring会自动注入dataSource Bean
        return bean;
    }
}
