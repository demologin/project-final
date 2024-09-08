package com.javarush.jira.common.internal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = """
                        <a href='http://localhost/'>JavaRush Jira application</a><br>
                        <p><b>Тестовые креденшелы:</b><br>
                        - user@gmail.com / password<br>
                        - admin@gmail.com / admin<br>
                        - guest@gmail.com / guest</p>
                        
                        <p><b>Добавление тегов к таскам:</b><br>
                        Без внятного ТЗ, результат - <b>ХЗ</b></br>
                        Добавление тегов не расписано от слова совсем, из разряда "ну ты сам как-нибудь придумай".</br>
                        В общем, вариантов реализации тут так много, что реализован базовый функционал:</br>
                        - Создание тегов вместе с задачей (опционально) </br>
                        - Получение тегов задачи </br>
                        - Добавление тегов к задаче (обязательно) </br>
                        - Перезапись тегов у задачи (обязательно) </br>
                        - Удаление тегов у задачи </br>
                        </p>
                        
                        <p><b>Разночтения в ТЗ:</b><br>
                        - теги нужно добавлять отдельно, или для каждой обозначенной операции?<br>
                        - при добавлении тега достаточно указать только ID задачи, или еще какую-то информацию о задаче?<br>
                        - теги нужно редактировать?<br>
                        - теги нужно удалять?<br>
                        - требуется ли облако тегов, где есть уже существующие теги? (для этого тег должен быть отдельной сущностью, а не просто строкой)<br>
                        - нужно ли существующие теги предлагать пользователю при добавлении?</p>
                        """,
                contact = @Contact(url = "https://javarush.com/about/contacts", email = "support@javarush.com")
        ),
        servers = {
                @Server(url = "${app.host-url}")
        },
        security = @SecurityRequirement(name = "basicAuth")
)
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}
