package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.ProfileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    ProfileMapper mapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUser() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(profileToUser));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(profileToAdmin));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void createGuestProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(profileToGuest));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateNullContacts() throws Exception {
        // get profileTo from test db
        Profile beforeUpdate = profileRepository.getExisted(USER_ID);
        ProfileTo beforeUpdateTo = mapper.toTo(beforeUpdate);
        // update
        beforeUpdateTo.setContacts(null);
        beforeUpdateTo.setMailNotifications(ProfileUtil.maskToNotifications(14));
        // send to service
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(beforeUpdateTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        // load from db updated profile
        Profile afterUpdate = profileRepository.getExisted(USER_ID);
        ProfileTo afterUpdateTo = mapper.toTo(afterUpdate);
        // get reference entity instance
        ProfileTo profileToUserUpdated = getProfileToUserUpdated();

        PROFILE_TO_MATCHER.assertMatch(profileToUserUpdated, afterUpdateTo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateSameContacts() throws Exception {
        // get profileTo from test db
        Profile beforeUpdate = profileRepository.getExisted(USER_ID);
        ProfileTo beforeUpdateTo = mapper.toTo(beforeUpdate);
        // get reference entity instance
        ProfileTo profileToUserUpdated = getProfileToUserUpdated();
        // update
        beforeUpdateTo.setContacts(profileToUserUpdated.getContacts());
        beforeUpdateTo.setMailNotifications(profileToUserUpdated.getMailNotifications());
        // send to service
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(beforeUpdateTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        // load from db updated profile
        Profile afterUpdate = profileRepository.getExisted(USER_ID);
        ProfileTo afterUpdateTo = mapper.toTo(afterUpdate);
        afterUpdateTo.setContacts(profileToUserUpdated.getContacts());

        PROFILE_TO_MATCHER.assertMatch(profileToUserUpdated, afterUpdateTo);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInconsistentId() throws Exception {
        // get profileTo from test db
        Profile beforeUpdate = profileRepository.getExisted(ADMIN_ID);
        ProfileTo beforeUpdateTo = mapper.toTo(beforeUpdate);
        // send to service
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(beforeUpdateTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                ;
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateUnexistsingContacts() throws Exception {
        // get profileTo from test db
        Profile beforeUpdate = profileRepository.getExisted(USER_ID);
        ProfileTo beforeUpdateTo = mapper.toTo(beforeUpdate);
        beforeUpdateTo.setContacts(Set.of(getTestContactTo()));
        // send to service
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(beforeUpdateTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
        ;
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void updateNewProfile() throws Exception {
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(profileToGuest)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile guestProfileSaved = profileRepository.getExisted(GUEST_ID);
        ProfileTo guestProfileSavedTo = mapper.toTo(guestProfileSaved);

        PROFILE_TO_MATCHER.assertMatch(profileToGuest, guestProfileSavedTo);
    }
}