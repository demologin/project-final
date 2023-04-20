package com.javarush.jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainer")
class JiraRushApplicationTests {
	@Test
	void contextLoads() {
	}

//    TODO added testcontainers
	@Container
	private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.2");

    //    TODO added dynamic source for testcontainers
	@DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
}
