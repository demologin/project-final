package com.javarush.jira.profile.web;

import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class ProfileRestControllerTest {
    @Autowired
    ProfileRestController profileRestController;
    @Mock
    AbstractProfileController abstractProfileController;

    @Mock
    ProfileTo profileTo;
    @Mock
    AuthUser authUser;

    @Test
    void shouldCallOneTimeSuperMethodGet_WhenCallsMethodGet(AuthUser authUser) {
        //given
        when(abstractProfileController.get(authUser.id())).thenReturn(profileTo);
        //when
        profileRestController.get(authUser.id());
        //then
        verify(abstractProfileController, times(1)).get(authUser.id());
    }

    @Test
    void shouldCallOneTimeSuperMethodUpdate_WhenCallsMethodUpdate() {
        //given
        doNothing().when(abstractProfileController).update(profileTo, authUser.id());
        //when
        profileRestController.update(profileTo, authUser.id());
        //then
        verify(abstractProfileController, times(1)).update(profileTo, authUser.id());
    }
}