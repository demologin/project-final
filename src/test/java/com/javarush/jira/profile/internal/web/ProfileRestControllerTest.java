package com.javarush.jira.profile.internal.web;

//import com.javarush.jira.common.util.JsonUtil;
//import com.javarush.jira.profile.ProfileTo;
//import com.javarush.jira.profile.internal.ProfileMapper;
//import com.javarush.jira.profile.internal.ProfileRepository;
//import com.javarush.jira.profile.internal.model.Profile;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(ProfileRestController.class)
//class ProfileRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProfileRepository profileRepository;
//
//    @MockBean
//    private ProfileMapper profileMapper;
//
//    @Test
//    @WithMockUser
//    void getProfile() throws Exception {
//        // Данные для мока
//        Profile profile = ProfileTestData.getNew(1L);
//        ProfileTo profileTo = ProfileTestData.USER_PROFILE_TO;
//
//        // Мокаем поведение репозитория и маппера
//        Mockito.when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile));
//        Mockito.when(profileMapper.toTo(profile)).thenReturn(profileTo);
//
//        // Выполняем GET-запрос и проверяем результат
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(JsonUtil.writeValue(profileTo)));
//    }
//
//    @Test
//    @WithMockUser
//    void updateProfile() throws Exception {
//        // Данные для обновления
//        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();
//        Profile updatedProfile = ProfileTestData.getUpdated(1L);
//
//        // Мокаем поведение маппера и репозитория
//        Mockito.when(profileRepository.findById(anyLong())).thenReturn(Optional.of(updatedProfile));
//        Mockito.when(profileMapper.updateFromTo(Mockito.any(Profile.class), Mockito.any(ProfileTo.class)))
//                .thenReturn(updatedProfile);
//
//        // Выполняем PUT-запрос на обновление и проверяем результат
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonUtil.writeValue(updatedTo)))
//                .andExpect(status().isNoContent());
//
//        Mockito.verify(profileRepository).save(updatedProfile);
//    }
//}

// первые два теста почти рабочие
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.internal.config.SecurityConfig;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import com.javarush.jira.profile.internal.web.ProfileTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(ProfileRestController.class)
@Import(SecurityConfig.class)  // Import your security configuration if needed
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUser authUser;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private ProfileRestController profileController;

    private final ObjectMapper objectMapper = new ObjectMapper();


//    @BeforeEach
//    void setUp() {
//        // Set up MockMvc with security configuration if needed
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(new ProfileRestController())
//                .apply(SecurityMockMvcConfigurers.springSecurity())
//                .build();
//    }

//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfileRestController()).build();
//
//    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetProf() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());
        System.out.println("User authorities: " + authentication.getAuthorities());
        Profile profile = ProfileTestData.getNew(1L);
        ProfileTo profileTo = ProfileTestData.USER_PROFILE_TO;
        System.out.println("<<<<<<<<  " + profileController.get(1L));
        when(profileRepository.getOrCreate(1L)).thenReturn(profile);
        when(profileController.get(1L)).thenReturn(profileTo);
        System.out.println(">>>>>>>>  " + profileController.get(1L));
        mockMvc.perform(get(ProfileRestController.REST_URL)
                        .with(user("user").roles("USER")))  // Добавляем пользователя с ролью
                .andExpect(status().isOk())
                .andDo(print())
//                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))

//                .andExpect(MockMvcResultMatchers.jsonPath("$.contacts[0].code").exists())

        ;
//                .andExpect(MockMvcResultMatchers.jsonPath("$.mailNotifications").exists())
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.contacts[0].code").value("mobile"));
    }

//        @Test
////    @WithMockUser(username = "user", roles = {"USER"})
//    void testGetProfile() throws Exception {
//        Profile profile = ProfileTestData.getNew(1L);
//        ProfileTo profileTo = ProfileTestData.USER_PROFILE_TO;
//
//        when(profileRepository.getOrCreate(1L)).thenReturn(profile);
//        when(profileController.get(1L)).thenReturn(profileTo);
//
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .principal(() -> "user"))  // Simulating authenticated user
//                .andExpect(status().isOk());
////                .andExpect(MockMvcResultMatchers.jsonPath("$.mailNotifications").exists());
////                .andExpect(MockMvcResultMatchers.jsonPath("$.contacts[0].code").value("skype"));
////
////        verify(profileRepository, times(1)).getOrCreate(1L);
////        verify(profileMapper, times(1)).toTo(profile);
//    }

//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void testUpdateProfile() throws Exception {
//        ProfileTo updatedProfileTo = ProfileTestData.getUpdatedTo();
//        Profile updatedProfile = ProfileTestData.getUpdated(1L);
//
//        when(profileRepository.getOrCreate(1L)).thenReturn(updatedProfile);
//        when(profileMapper.updateFromTo(updatedProfile, updatedProfileTo)).thenReturn(updatedProfile);
//
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedProfileTo))
//                        .principal(() -> "user"))  // Simulating authenticated user
//                .andExpect(status().isNoContent());
//
//        verify(profileRepository, times(1)).getOrCreate(1L);
//        verify(profileMapper, times(1)).updateFromTo(updatedProfile, updatedProfileTo);
//    }

}

//
//import com.javarush.jira.AbstractControllerTest;
//import com.javarush.jira.common.util.profile.WithProfileDetails;
//import com.javarush.jira.login.AuthUser;
//import com.javarush.jira.profile.ProfileTo;
//import com.javarush.jira.profile.internal.ProfileMapper;
//import com.javarush.jira.profile.internal.ProfileRepository;
//import com.javarush.jira.profile.internal.model.Profile;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Profile;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
////import static org.testcontainers.shaded.org.hamcrest.Matchers.hasSize;
//
////@AutoConfigureMockMvc
//class ProfileRestControllerTest extends AbstractControllerTest {
//
//    @Mock
//    private AuthUser authUser;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private ProfileRepository profileRepository;
//
//    @Mock
//    private ProfileMapper profileMapper;
//
//    @InjectMocks
//    private ProfileRestController profileRestController;
//
//    ProfileRestControllerTest() {
////        this.mockMvc = MockMvcBuilders.standaloneSetup(profileRestController).build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfileRestController()).build();
//    }
//
//    @Test
//    void testGetProfile() throws Exception {
//        Profile profile = ProfileTestData.getNew(1L);
//        ProfileTo profileTo = ProfileTestData.USER_PROFILE_TO;
//
//        when(profileRepository.getOrCreate(1L)).thenReturn(profile);
//        when(profileMapper.toTo(profile)).thenReturn(profileTo);
//
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .principal(() -> "user")  // Simulating authenticated user
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk());
////
////        verify(profileRepository, times(1)).getOrCreate(1L);
////        verify(profileMapper, times(1)).toTo(profile);
//    }
//
//    @Test
//    void testUpdateProfile() throws Exception {
//        ProfileTo updatedProfileTo = ProfileTestData.getUpdatedTo();
//        Profile updatedProfile = ProfileTestData.getUpdated(1L);
//
//        when(profileRepository.getOrCreate(1L)).thenReturn(updatedProfile);
//        when(profileMapper.updateFromTo(updatedProfile, updatedProfileTo)).thenReturn(updatedProfile);
//
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .principal(() -> "user")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
////                        .content("{ /* JSON representation of ProfileTo */ }"))  // Replace with valid JSON for ProfileTo
//                .andExpect(status().isNoContent());

//        verify(profileRepository, times(1)).getOrCreate(1L);
//        verify(profileMapper, times(1)).updateFromTo(updatedProfile, updatedProfileTo);
//    }


//    @Test
//    @WithProfileDetails(ProfileTestData.USER_PROFILE_TO)
//    void testUserProfileTo() throws Exception {
////        authUser = ProfileTestData.USER_PROFILE_TO.
////        profileTo = profileRestController.get(ProfileTestData.USER_PROFILE_TO)
//        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL + "profile?continue"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//        ;
//    }

//    @Test
//    void testUserProfileTo() throws Exception {
//        profileTo = ProfileTestData.USER_PROFILE_TO;
//        mockMvc.perform(get("/api/profile"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void testGetProfileSuccess() throws Exception {
//        // Подготовка данных
//        profileRepository.getOrCreate(1L);
//        mockMvc.perform(get(ProfileRestController.REST_URL)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//        ;
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void getProfileIntegrationTest() throws Exception {
//        mockMvc.perform(get(ProfileRestController.REST_URL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//        ;
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void updateProfileIntegrationTest() throws Exception {
//        mockMvc.perform(put(ProfileRestController.REST_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"id\": \"1\", \"name\": \"Updated Name\" }"))
//                .andExpect(status().isNoContent());
//    }

//}