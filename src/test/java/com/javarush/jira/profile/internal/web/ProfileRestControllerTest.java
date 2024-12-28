package com.javarush.jira.profile.internal.web;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getNewGuestProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(GUEST_PROFILE_EMPTY_TO));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        ProfileTo updatingProfileTo = getUpdatedTo();
        Profile expectedProfile = getUpdated(USER_ID);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile actualProfile = repository.findById(USER_ID).orElse(null);
        PROFILE_MATCHER.assertMatch(actualProfile, expectedProfile);
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void updateWithNewProfileTo() throws Exception {
        ProfileTo updatingProfileTo = getNewTo();
        Profile expectedProfile = getNew(GUEST_ID);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile actualProfile = repository.findById(GUEST_ID).orElse(null);
        PROFILE_MATCHER.assertMatch(actualProfile, expectedProfile);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithInvalidTo() throws Exception {
        ProfileTo updatingProfileTo = getInvalidTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownContactTo() throws Exception {
        ProfileTo updatingProfileTo = getWithUnknownContactTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownNotificationTo() throws Exception {
        ProfileTo updatingProfileTo = getWithUnknownNotificationTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithContactHtmlUnsafeTo() throws Exception {
        ProfileTo updatingProfileTo = getWithContactHtmlUnsafeTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnConsistentTo() throws Exception {
        ProfileTo updatingProfileTo = getUnConsistentTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnConsistentContactTo() throws Exception {
        ProfileTo updatingProfileTo = getWithUnConsistentContactTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatingProfileTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void updateUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getNewTo())))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}