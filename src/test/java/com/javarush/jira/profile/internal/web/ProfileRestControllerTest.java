package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.USER_ID;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO p5 - tests for ProfileRestController.class
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileMapper profileMapper;

    // Test unauthorized access
    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    // Test access with guest profile
    @Test
    void getGuestProfile() throws Exception {
        ProfileTo expectedProfile = GUEST_PROFILE_EMPTY_TO;

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(PROFILE_MATCHER.contentJson(expectedProfile));
    }

    // Test access with user profile
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAuthorized() throws Exception {
        USER_PROFILE_TO.setId(USER_ID);

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(PROFILE_MATCHER.contentJson(USER_PROFILE_TO));
    }

    // Test creating a new profile
    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNewProfile() throws Exception {
        ProfileTo newProfileTo = getNewTo();
        Profile expectedProfile = getNew(USER_ID);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newProfileTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile createdProfile = repository.findById(USER_ID).orElseThrow();
        assertThat(createdProfile).usingRecursiveComparison().isEqualTo(expectedProfile);
    }

    // Test updating a profile
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateProfile() throws Exception {
        Profile profileToUpdate = getUpdated(USER_ID);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(profileMapper.toTo(profileToUpdate))))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile updatedProfile = repository.findById(USER_ID).orElseThrow();
        assertThat(profileMapper.toTo(updatedProfile)).usingRecursiveComparison().isEqualTo(profileMapper.toTo(profileToUpdate));
    }

    // Test updating a profile with new data
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateProfileWithNewData() throws Exception {
        ProfileTo updatedProfileTo = getUpdatedTo();
        Profile expectedProfile = getUpdated(USER_ID);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedProfileTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile updatedProfile = repository.findById(USER_ID).orElseThrow();
        assertThat(updatedProfile).usingRecursiveComparison().isEqualTo(expectedProfile);
    }

    // Test updating a profile with invalid data
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithInvalidProfile() throws Exception {
        ProfileTo invalidProfile = getInvalidTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidProfile)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("invalid_params")));
    }

    // Test updating a profile with an unknown notification
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownNotification() throws Exception {
        ProfileTo unknownNotificationProfile = getWithUnknownNotificationTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(unknownNotificationProfile)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Value with key WrongNotification not found")));
    }

    // Test updating a profile with an unknown contact
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithUnknownContact() throws Exception {
        ProfileTo unknownContactProfile = getWithUnknownContactTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(unknownContactProfile)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Value with key WrongContactCode not found")));
    }

    // Test updating a profile with HTML unsafe contact
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithHtmlUnsafeContact() throws Exception {
        ProfileTo htmlUnsafeContactProfile = getWithContactHtmlUnsafeTo();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(htmlUnsafeContactProfile)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("{error.noHtml}")));
    }

}
