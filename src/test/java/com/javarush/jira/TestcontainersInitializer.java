package com.javarush.jira;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/*TODO Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL.
        Для этого нужно определить 2 бина, и выборка какой из них использовать должно определяться активным профилем Spring.
        (сделано с помощью тест контейнеров)
        Александр, не разобрался как поднять тестконтейнер на тестовом скрипте, подскажите как переопределить путь скриптов?*/
public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"));

    static {
        postgres.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

}
