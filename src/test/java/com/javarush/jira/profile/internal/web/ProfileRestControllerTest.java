package com.javarush.jira.profile.internal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {
//TODO 5. Add two test methods for ProfileRestController, i'm confused a little bit, that's why only two for now;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @MockBean
    private ProfileRepository profileRepository;

    @Mock
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        authUser = mock(AuthUser.class);
        when(authUser.id()).thenReturn(1L);
    }

    @Test
    void getSuccess() throws Exception {
        Profile profile = ProfileTestData.getNew(authUser.id());

        when(profileRepository.getOrCreate(authUser.id())).thenReturn(profile);

        perform(MockMvcRequestBuilders
                .get(ProfileRestController.REST_URL)
                .with(user(authUser)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(profileMapper.toTo(profile))));
    }

    @Test
    void getFailure() throws Exception {

        when(profileRepository.getOrCreate(authUser.id())).thenThrow(new NotFoundException("error"));

        perform(MockMvcRequestBuilders
                .get(ProfileRestController.REST_URL)
                .with(user(authUser)))
                .andExpect(status().isNotFound());
    }




}

