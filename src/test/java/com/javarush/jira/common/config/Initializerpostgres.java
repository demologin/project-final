package com.javarush.jira.common.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
//TODO: Задача №5
public class Initializerpostgres {
    public  static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2");
public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}

}
