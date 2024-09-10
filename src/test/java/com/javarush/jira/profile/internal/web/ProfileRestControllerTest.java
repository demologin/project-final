package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.EnableTestContainers;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableTestContainers
class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithAdminEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        ProfileTo updatedTo = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(getUpdatedTo()));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        ProfileTo updatedTo = getInvalidTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownNotificationTo() throws Exception {
        ProfileTo updatedTo = getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownContactTo() throws Exception {
        ProfileTo updatedTo = getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithContactHtmlUnsafeTo() throws Exception {
        ProfileTo updatedTo = getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNew() throws Exception {
        ProfileTo newTo = getNewTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(APPLICATION_JSON).content(writeValue(newTo)))
                .andExpect(status().is2xxSuccessful());
    }





}