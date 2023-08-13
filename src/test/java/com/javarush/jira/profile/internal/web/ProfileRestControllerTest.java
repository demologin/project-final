package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.BaseHandler.REST_URL;
import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO - Написать тесты для всех публичных методов контроллера ProfileRestController.

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository repository;
    private static final String PROFILE_REST_URL = REST_URL + "/profile";

    public static final String USER_MAIL = "user@gmail.com";

    public static final String GUEST_MAIL = "guest@gmail.com";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get_user_ok() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER_TO.contentJson(USER_PROFILE_TO))
        ;
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void get_guest_ok() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER_TO.contentJson(GUEST_PROFILE_EMPTY_TO))
        ;
    }
    @Test
    void get_unauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_InvalidProfileTo() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getInvalidTo())));

        resultActions
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_UnknownNotificationTo() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithUnknownNotificationTo())));

        resultActions
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_UnknownContactTo() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithUnknownContactTo())));

        resultActions
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_ContactHtmlUnsafeTo() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithContactHtmlUnsafeTo())));

        resultActions
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_ok() throws Exception {
        Profile dbProfileBefore = repository.getExisted(1L);

        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())));

        Profile dbProfileAfter = repository.getExisted(1L);

        resultActions
                .andDo(print())
                .andExpect(status().isNoContent());

        assertNotEquals(dbProfileBefore.getContacts().size(), dbProfileAfter.getContacts().size());
        PROFILE_MATCHER.assertMatch(getUpdated(1L), dbProfileAfter);
    }
    @Test
    void update_unauthorized() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getInvalidTo())));

        resultActions
                .andExpect(status().isUnauthorized());
    }
}
