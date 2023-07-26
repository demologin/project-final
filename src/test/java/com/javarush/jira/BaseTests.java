package com.javarush.jira;

import com.javarush.jira.initializer.PostgresTestContainersInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

// TODO task 4 - testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        PostgresTestContainersInitializer.Initializer.class
})
abstract class BaseTests {

    @BeforeAll
    static void init(){
        PostgresTestContainersInitializer.container.start();
    }
}
