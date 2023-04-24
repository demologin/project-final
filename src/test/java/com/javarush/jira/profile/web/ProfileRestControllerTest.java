package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.USER_MATCHER;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.bouncycastle.asn1.cms.CMSAttributes.contentType;

class ProfileRestControllerTest  extends AbstractControllerTest {
    @Autowired
    protected ProfileMapper profileMapper;
    @Autowired
    private ProfileRepository profileRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(profile));
    }

    @Test
    void update() throws Exception {
        Profile profileBefore = profileRepository.getExisted(PROFILE_ID);
        ProfileTo updatedTo = profileMapper.toTo(getUpdated());
        updatedTo.setId(999L);
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, updatedTo.getId())))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profileAfter = profileRepository.getExisted(PROFILE_ID);
        assertEquals(profileBefore.getId(),updatedTo.getId(),"profile's id must not be changed");
        Profile updated = getUpdated();
        updated.setMailNotifications(profileBefore.getMailNotifications());
        PROFILE_MATCHER.assertMatch(profileBefore,updated);
    }
}