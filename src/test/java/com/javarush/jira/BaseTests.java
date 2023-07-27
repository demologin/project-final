package com.javarush.jira;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

//TODO Task 4: Tests modified so that testcontainers are used during tests.
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {BaseTests.Initializer.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data4tests/data.sql"}, config = @SqlConfig(encoding = "UTF-8"))
abstract class BaseTests {

    private static final String DATABASE_NAME = "jira-test";

    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:11.1")
                    .withDatabaseName(DATABASE_NAME);

    static {
        postgreSQLContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl(),
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
