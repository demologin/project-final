package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.javarush.jira.bugtracking.UserBelongServiceTestData.SPRINT_TO;
import static com.javarush.jira.bugtracking.UserBelongServiceTestData.USER_TYPE;

class UserBelongServiceTest extends AbstractControllerTest {

    @Autowired
    protected UserBelongRepository userBelongRepository;
    @Autowired
    protected UserRepository userRepository;
    @Test
    void testSubscribe() {

        Long USER_ID = 1L;

        List<UserBelong> allUserBelongs = userBelongRepository.getAllByUserId(USER_ID);
        Assertions.assertEquals(allUserBelongs.size(), 0L);

        User dbUser = userRepository.getExisted(USER_ID);
        Assertions.assertNotNull(dbUser);

        UserBelong userBelong = new UserBelong();
        userBelong.setObjectId(SPRINT_TO.getId());
        userBelong.setObjectType(SPRINT_TO.getObjectType());
        userBelong.setUserId(1L);
        userBelong.setUserTypeCode(USER_TYPE);
        userBelong.setStartpoint(LocalDateTime.now());
        userBelong.setEndpoint(null);
        userBelongRepository.save(userBelong);

        List<UserBelong> allUserBelongsUpdated = userBelongRepository.getAllByUserId(USER_ID);
        Assertions.assertEquals(allUserBelongsUpdated.size(), 1L);
    }

}