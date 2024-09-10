package com.javarush.jira;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("test")
@ActiveProfiles("h2dbtest")
abstract class BaseTests {
}
