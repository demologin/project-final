package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.config.Initializerpostgres;
import com.javarush.jira.profile.ProfileTo;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//TODO: Задача №6
class ProfileRestControllerTest  extends AbstractControllerTest {

    @Autowired
    protected ProfileMapper profileMapper;

    @BeforeAll
    static void init(){
        Initializerpostgres.postgreSQLContainer.start();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
     perform(MockMvcRequestBuilders.get("/api/profile"))
             .andExpect(status().isOk())
             .andDo(print())
             .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
             .andExpect(PROFILE_MATCHER.contentJson(profileTo));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        ProfileTo updateTo = getUpdated();
        updateTo.setId(null);
        perform(MockMvcRequestBuilders.put("/api/profile")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonWithId(updateTo,String.valueOf(updateTo.getId()))))
                .andDo(print())
                .andExpect(status().isNoContent());
        ProfileTo updatedTo2 = getUpdated();
        updateTo.setId(updatedTo2.getId());
        PROFILE_MATCHER.assertMatch(updateTo,updatedTo2);
    }
}