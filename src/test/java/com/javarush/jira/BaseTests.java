package com.javarush.jira;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class BaseTests {
    static final PostgreSQLContainer<?> CONTAINER_POSTGRES;

    static {
        CONTAINER_POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
        CONTAINER_POSTGRES.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER_POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER_POSTGRES::getUsername);
        registry.add("spring.datasource.password", CONTAINER_POSTGRES::getPassword);
    }
}
