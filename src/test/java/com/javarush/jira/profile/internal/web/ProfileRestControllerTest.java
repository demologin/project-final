package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    protected ProfileMapper profileMapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUserAuthorised() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAdminAuthorised() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(ADMIN_PROFILE_TO));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    @Transactional
    void successfulUpdateProfileUser() throws Exception {
        Profile userProfileInDbBefore = profileRepository.getExisted(USER_ID);
        ProfileTo userProfileToInDbBefore = profileMapper.toTo(userProfileInDbBefore);
        userProfileToInDbBefore.setMailNotifications(USER_UPDATED_MAIL_NOTIFICATIONS);
        userProfileToInDbBefore.setContacts(USER_UPDATED_CONTACTS);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(userProfileToInDbBefore)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile userProfileInDbAfter = profileRepository.getExisted(USER_ID);
        ProfileTo userProfileToInDbAfter = profileMapper.toTo(userProfileInDbAfter);
        assertEquals(userProfileToInDbAfter, getUpdatedUserTo());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    @Transactional
    void successfulUpdateProfileAdmin() throws Exception {
        Profile adminProfileInDbBefore = profileRepository.getExisted(ADMIN_ID);
        ProfileTo adminProfileToInDbBefore = profileMapper.toTo(adminProfileInDbBefore);
        adminProfileToInDbBefore.setMailNotifications(ADMIN_UPDATED_MAIL_NOTIFICATIONS);
        adminProfileToInDbBefore.setContacts(ADMIN_UPDATED_CONTACTS);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(adminProfileToInDbBefore)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile adminProfileInDbAfter = profileRepository.getExisted(ADMIN_ID);
        ProfileTo adminProfileToInDbAfter = profileMapper.toTo(adminProfileInDbAfter);
        assertEquals(adminProfileToInDbAfter, getUpdatedAdminTo());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    @Transactional
    void unsuccessfulUpdateWithInvalidTo() throws Exception {
        ProfileTo invalidTo = getInvalidTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    @Transactional
    void unsuccessfulUpdateWithUnknownNotificationTo() throws Exception {
        ProfileTo unknownNotificationTo = getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(unknownNotificationTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    @Transactional
    void unsuccessfulUpdateWithUnknownContactTo() throws Exception {
        ProfileTo unknownContactTo = getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(unknownContactTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    @Transactional
    void unsuccessfulUpdateContactHtmlUnsafeTo() throws Exception {
        ProfileTo unknownContactHtmlUnsafeTo = getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(unknownContactHtmlUnsafeTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}