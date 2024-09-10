package com.javarush.jira.common.internal.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean
    @Profile("postgres")
    public DataSource pgDataSource(@Value("${spring.datasource.password}") String password,
                                   @Value("${spring.datasource.username}") String username,
                                   @Value("${spring.datasource.url}") String url) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    @Profile("h2dbtest")
    public DataSource mysqlDataSource(@Value("${spring.datasource.password}") String password,
                                      @Value("${spring.datasource.username}") String username,
                                      @Value("${spring.datasource.url}") String url) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
