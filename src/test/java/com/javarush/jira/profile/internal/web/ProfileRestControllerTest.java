package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {


//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AbstractProfileController profileController;
//
//    @Test
//    void testGetProfileSuccess() throws Exception {
//        AuthUser authUser = mock(AuthUser.class);
//        ProfileTo profileTo = ProfileTestData.USER_PROFILE_TO;
//
//        when(authUser.id()).thenReturn(1L);
//        when(profileController.get(1L)).thenReturn(profileTo);
//
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .principal(new UsernamePasswordAuthenticationToken(authUser, null)))
//                .andExpect(status().isOk())
//                .andExpect(ProfileTestData.PROFILE_MATCHER.contentJson(profileTo));
//    }
//
//    @Test
//    void testGetProfileFailure() throws Exception {
//        AuthUser authUser = mock(AuthUser.class);
//
//        when(authUser.id()).thenReturn(1L);
//        when(profileController.get(1L)).thenThrow(new NotFoundException("Profile not found"));
//
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .principal(new UsernamePasswordAuthenticationToken(authUser, null)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testUpdateProfileSuccess() throws Exception {
//        AuthUser authUser = mock(AuthUser.class);
//        ProfileTo updatedProfileTo = ProfileTestData.getUpdatedTo();
//
//        when(authUser.id()).thenReturn(1L);
//
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .principal(new UsernamePasswordAuthenticationToken(authUser, null))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonUtil.writeValue(updatedProfileTo)))
//                .andExpect(status().isNoContent());
//
//        verify(profileController).update(updatedProfileTo, 1L);
//    }
//
//    @Test
//    void testUpdateProfileFailure() throws Exception {
//        AuthUser authUser = mock(AuthUser.class);
//        ProfileTo invalidProfileTo = ProfileTestData.getInvalidTo();
//
//        when(authUser.id()).thenReturn(1L);
//
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .principal(new UsernamePasswordAuthenticationToken(authUser, null))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonUtil.writeValue(invalidProfileTo)))
//                .andExpect(status().isBadRequest());
//    }

}