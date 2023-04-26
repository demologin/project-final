package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.UserTo;
import com.javarush.jira.profile.ProfileService;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;

import com.javarush.jira.ref.RefType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserController.REST_URL;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileMapper mapper;
    private static final String REST_URL = ProfileRestController.REST_URL;


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(userProfile));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Profile dbProfilerBefore = profileRepository.getOrCreate(PROFILE_ID);
        ProfileTo updatedTo = mapper.toTo(dbProfilerBefore);
        updatedTo.setContacts(Set.of(mobile, website, skype, github));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile dbProfilerAfter = profileRepository.getOrCreate(PROFILE_ID);
        ProfileTo updatedAfter = mapper.toTo(dbProfilerAfter);
        assertEquals(updatedTo.getContacts(), updatedAfter.getContacts(), "profile contacts must not be changed");
    }
}