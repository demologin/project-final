package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.BaseHandler;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_PROFILE = BaseHandler.REST_URL + "/profile";

    @Test
    @WithUserDetails(USER_MAIL)
    void whenGetWithAuthorizedUser_thenStatusIsOk() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    void whenGetWithUnauthorizedUser_thenStatusIsUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void whenUpdateWithAuthUserAndCorrectData_thenStatusIsNoContent() throws Exception {
        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();
        String json = JsonUtil.writeValue(updatedTo);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void whenUpdateWithUnauthorizedUser_thenStatusIsUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void whenUpdateWithInvalidTo_thenStatusIs4xx() throws Exception {
        ProfileTo invalidTo = ProfileTestData.getInvalidTo();
        String json = JsonUtil.writeValue(invalidTo);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void whenUpdateWithUnknownNotificationTo_thenStatusIs4xx() throws Exception {
        ProfileTo profileTo = ProfileTestData.getWithUnknownNotificationTo();
        String json = JsonUtil.writeValue(profileTo);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void whenUpdateWithUnknownContactTo_thenStatusIs4xx() throws Exception {
        ProfileTo profileTo = ProfileTestData.getWithUnknownContactTo();
        String json = JsonUtil.writeValue(profileTo);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void whenUpdateWithContactHtmlUnsafeTo_thenStatusIs4xx() throws Exception {
        ProfileTo profileTo = ProfileTestData.getWithContactHtmlUnsafeTo();
        String json = JsonUtil.writeValue(profileTo);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}