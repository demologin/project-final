package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.internal.web.UserTestData;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {
    public static final String URL_FROM_CONTROLLER = ProfileRestController.REST_URL;

    @Test
    void getUnAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_FROM_CONTROLLER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateUnAuthorized() throws Exception {
        ProfileTo updateProfileTo = ProfileTestData.getInvalidTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updateProfileTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAuthorizedReturnJson() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_FROM_CONTROLLER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        ProfileTo profileUpdateTo = ProfileTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(profileUpdateTo)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getInvalidToUser() throws Exception {
        ProfileTo invalidProfileTo = ProfileTestData.getInvalidTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(invalidProfileTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getUnknownNotificationTo() throws Exception {
        ProfileTo profileToUnknownNotification = ProfileTestData.getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(profileToUnknownNotification)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getUnknownContactTo() throws Exception {
        ProfileTo profileToUnknownContact = ProfileTestData.getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(profileToUnknownContact)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getContactHtmlUnsafeTo() throws Exception {
        ProfileTo contactHtmlUnsafeTo = ProfileTestData.getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(URL_FROM_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(contactHtmlUnsafeTo)))
                .andExpect(status().isUnprocessableEntity());
    }
}