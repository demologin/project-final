package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.config.Initializerpostgres;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest  extends AbstractControllerTest {
    @Autowired
    protected ProfileMapper profileMapper;
    @Autowired
    private ProfileRepository profileRepository;
    @BeforeAll
    static void init(){
        Initializerpostgres.postgreSQLContainer.start();
    }

//    @Test
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
//                //.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(PROFILE_MATCHER.contentJson(profile));
//    }

//    @Test
//    void update() throws Exception {
//        Profile profileBefore = profileRepository.getExisted(PROFILE_ID);
//        ProfileTo updatedTo = profileMapper.toTo(getUpdated());
//        updatedTo.setId(999L);
//        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonWithPassword(updatedTo, updatedTo.getId())))
//                .andDo(print());
//             //   .andExpect(status().isNoContent());
//
//        Profile profileAfter = profileRepository.getExisted(PROFILE_ID);
//        assertEquals(profileBefore.getId(),updatedTo.getId(),"profile's id must not be changed");
//        Profile updated = getUpdated();
//        updated.setMailNotifications(profileBefore.getMailNotifications());
//        PROFILE_MATCHER.assertMatch(profileBefore,updated);
//    }
}