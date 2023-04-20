package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.javarush.jira.profile.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static com.javarush.jira.profile.web.ProfileTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO test ProfileRestController
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ProfileMapper profileMapper;

    @Autowired
    private TestRestTemplate template;


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(profileMapper.toTo(profile)));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Profile dbProfileBefore = profileRepository.getOrCreate(USER_ID);
        ProfileTo dbProfileToBefore = profileMapper.toTo(dbProfileBefore);

        Profile updated = getUpdated();
        ProfileTo updatedTo = profileMapper.toTo(updated);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        template.withBasicAuth("user@gmail.com", "password").put(REST_URL, updatedTo);
        ProfileTo dbProfileToAfter = template.withBasicAuth("user@gmail.com", "password").getForObject(REST_URL, ProfileTo.class);

        PROFILE_MATCHER.assertMatch(updatedTo, dbProfileToAfter);
        template.withBasicAuth("user@gmail.com", "password").put(REST_URL, dbProfileToBefore);
    }
}
