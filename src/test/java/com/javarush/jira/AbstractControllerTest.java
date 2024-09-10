package com.javarush.jira;

import com.javarush.jira.common.jwt.service.JwtService;
import com.javarush.jira.login.AuthUser;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_MAIL;

//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications
@Sql(scripts = {"classpath:db/changelog.sql", "classpath:data.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@AutoConfigureMockMvc
//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment
public abstract class AbstractControllerTest extends BaseTests implements MockMvcBuilderCustomizer {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Autowired
    JwtService jwtService;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @WithUserDetails(value = ADMIN_MAIL)
    @Before
    public void init(){
        this.token = jwtService.generateToken((AuthUser) SecurityContextHolder.getContext().getAuthentication());
    }


    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        RequestBuilder apiKeyRequestBuilder = MockMvcRequestBuilders.get("any")
                .header("Authorization", "Bearer " + token);
        builder.defaultRequest(apiKeyRequestBuilder);
    }
}
