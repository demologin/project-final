package com.javarush.jira;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(TestContainerSetup.class)
@ActiveProfiles("test")
abstract class BaseTests {
}
