package com.javarush.jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = JiraRushApplicationTests.TestContainerInitializer.class)
class JiraRushApplicationTests {

	@Container
	static JdbcDatabaseContainer<?> CONTAINER;

	static {
		CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2"));
		CONTAINER.start();
	}

	public static class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + CONTAINER.getJdbcUrl(),
					"spring.datasource.username=" + CONTAINER.getUsername(),
					"spring.datasource.password=" + CONTAINER.getPassword()
			).applyTo(applicationContext.getEnvironment());
		}
	}

	@Test
	void contextLoads() {
	}
}
