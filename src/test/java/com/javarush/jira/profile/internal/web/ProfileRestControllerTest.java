package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
class ProfileRestControllerTest extends AbstractControllerTest {

    private final ProfileMapper mapper;
    private final ProfileRepository repository;

    private static final String REST_URL = "/api/profile";
    private static final MatcherFactory.Matcher<ProfileTo> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class, "contacts"
    );

    @Test
    @WithUserDetails(USER_MAIL)
    void get_shouldReturnUserProfileJson_whenAuthUserTryToGetProfile() throws Exception {
        ProfileTo profileTo = mapper.toTo(repository.getOrCreate(user.id()));

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(profileTo));
    }

    @Test
    @WithAnonymousUser
    void get_shouldReturnStatusIsUnauthorized_whenNotAuthUserTryToGetProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(GUEST_MAIL)
    void update_shouldReturnStatusIsNoContent_whenAuthUserTryToUpdateProfileWithGuestRoles() throws Exception {
        ProfileTo profileTo = mapper.toTo(repository.getOrCreate(guest.id()));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .content(JsonUtil.writeValue(profileTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void update_shouldReturnStatusIsNoContent_whenAuthUserTryToUpdateProfileWithUserRoles() throws Exception {
        ProfileTo profileTo = mapper.toTo(repository.getOrCreate(user.id()));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .content(JsonUtil.writeValue(profileTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(MANAGER_MAIL)
    void update_shouldReturnStatusIsNoContent_whenAuthUserTryToUpdateProfileWithManagerRoles() throws Exception {
        ProfileTo profileTo = mapper.toTo(repository.getOrCreate(manager.id()));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .content(JsonUtil.writeValue(profileTo))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update_shouldReturnStatusIsUnprocessableEntity_whenInvalidUserEmail() throws Exception {
        manager.setEmail("@");
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(manager)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        manager.setEmail(MANAGER_MAIL);
    }
}