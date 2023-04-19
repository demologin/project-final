import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Container implements ApplicationContextInitializer<ConfigurableApplicationContext>{
    @org.testcontainers.junit.jupiter.Container
    private final static PostgreSQLContainer<?> postgreSQLContainer=new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("jiraTest")
            .withUsername("testName")
            .withPassword("jiraRushTest")
            .withInitScript("test.sql");
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
