package com.javarush.jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JiraRushApplicationTests {

    @Test
    void contextLoads() {
    }/*

    @Container
    public static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer(
            DockerImageName.parse("postgres:15.2")
                    .asCompatibleSubstituteFor("postgresTestcontainers")
    )
            .withDatabaseName("db")
            .withUsername("test_user")
            .withPassword("test_password");

    @BeforeAll
    public static void setup() {
        postgresqlContainer.setWaitStrategy(
                new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(1)
                        .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS))
        );
        postgresqlContainer.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgresqlContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgresqlContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", postgresqlContainer::getDriverClassName);
    }*/
}
