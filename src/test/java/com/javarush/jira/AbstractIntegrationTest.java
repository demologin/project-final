package com.javarush.jira;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    static {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
                .withDatabaseName("jira-test")
                .withUsername("jira")
                .withPassword("JiraRush");
        postgreSQLContainer.start();
        System.out.println("=================================TestContainer started, TestContainer url >>>> " + postgreSQLContainer.getJdbcUrl());
    }
}