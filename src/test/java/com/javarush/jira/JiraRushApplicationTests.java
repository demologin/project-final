package com.javarush.jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest
@Sql(scripts = {"classpath:db/changelog/init-schema.sql", "classpath:db/test.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("test")
class JiraRushApplicationTests {
	@Test
	void contextLoads() {
	}
}
