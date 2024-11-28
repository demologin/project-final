package com.javarush.jira;

import com.javarush.jira.common.internal.config.AppProperties;
import com.javarush.jira.common.internal.config.EnvVariablesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class JiraRushApplication {

    public static void main(String[] args) {
        EnvVariablesLoader.loadEnvVariables();

        SpringApplication.run(JiraRushApplication.class, args);
    }
}
