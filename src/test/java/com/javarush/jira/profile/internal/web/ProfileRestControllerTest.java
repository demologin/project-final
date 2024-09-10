package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.internal.web.UserTestData;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = ProfileRestController.REST_URL;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProfileTestData.PROFILE_TO_MATCHER.contentJson(ProfileTestData.USER_PROFILE_TO));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProfileTestData.PROFILE_TO_MATCHER.contentJson(ProfileTestData.getUpdatedTo()));
    }

    @Test
    void updateUnauthorized() throws Exception {
        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateInvalidTo() throws Exception {
        ProfileTo invalidTo = ProfileTestData.getInvalidTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateWithUnknownNotificationTo() throws Exception {
        ProfileTo unknownNotificationTo = ProfileTestData.getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(unknownNotificationTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateWithUnknownContactTo() throws Exception {
        ProfileTo unknownContactTo = ProfileTestData.getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(unknownContactTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateWithContactHtmlUnsafeTo() throws Exception {
        ProfileTo contactHtmlUnsafeTo = ProfileTestData.getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(contactHtmlUnsafeTo)))
                .andExpect(status().isUnprocessableEntity());
    }
}