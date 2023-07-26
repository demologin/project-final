package com.javarush.jira;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
// Todo Task 4 - add annotation for initialization TestContainer class
@ContextConfiguration(initializers = TestcontainersInitializer.class)
abstract class BaseTests {
}
