package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.GUEST_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL;

    @Autowired
    ProfileMapper profileMapper;
    @Autowired
    ProfileRepository profileRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void shouldGetProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void shouldGetGuestProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(GUEST_PROFILE_EMPTY_TO));
    }

    @Test
    void getUnAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNewProfile() throws Exception {
        AuthUser auth = AuthUser.safeGet();
        Profile newProfile = ProfileTestData.getNew(auth.id());
        profileRepository.save(newProfile);

        ResultActions resultActions = perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ProfileTo resp = PROFILE_TO_MATCHER.readFromJson(resultActions);
        ProfileTo newProfileTo = ProfileTestData.getNewTo();
        newProfileTo.setId(resp.getId());
        PROFILE_TO_MATCHER.assertMatch(resp, newProfileTo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void shouldUpdateProfile() throws Exception {
        AuthUser auth = AuthUser.safeGet();
        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();
        updatedTo.setId(auth.id());
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        Profile updatedInRepo = profileRepository.findById(updatedTo.id()).get();
        Profile updatedFromRepo = ProfileTestData.getUpdated(auth.id());
        PROFILE_MATCHER.assertMatch(updatedInRepo, updatedFromRepo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalidProfileTo() throws Exception {
        ProfileTo invalidTo = ProfileTestData.getInvalidTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownNotification() throws Exception {
        ProfileTo invalidTo = ProfileTestData.getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownContactTo() throws Exception {
        ProfileTo invalidTo = ProfileTestData.getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnsafeContactHTML() throws Exception {
        ProfileTo profileTo = ProfileTestData.getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(profileTo)))
                .andExpect(status().isUnprocessableEntity());
    }


}