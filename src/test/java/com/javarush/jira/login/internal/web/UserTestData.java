package com.javarush.jira.login.internal.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.UserTo;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Contact;
import com.javarush.jira.profile.internal.Profile;

import java.util.List;
import java.util.Set;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            User.class, "startpoint", "endpoint", "password");
    public static final MatcherFactory.Matcher<UserTo> TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserTo.class);
    //TODO добавил матчер для профиля
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class);

    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;
    public static final long GUEST_ID = 3;
    public static final long PROFILE_ID = 1;
    public static final long NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, USER_MAIL, "userPassword", "userFirstName", "userLastName",
            "userDisplayName", Role.DEV);
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "adminPassword", "adminFirstName", "adminLastName",
            "adminDisplayName", Role.ADMIN, Role.DEV);
    public static final User guest = new User(GUEST_ID, GUEST_MAIL, "guestPassword", "guestFirstName", "guestLastName",
            "guestDisplayName");
    //TODO добавил статических переменных для проверок
    public static final ContactTo mobile = new ContactTo("mobile", "+01234567890");
    public static final ContactTo website = new ContactTo("website", "user.com");
    public static final ContactTo skype = new ContactTo("skype", "userSkype");
    public static final ContactTo github = new ContactTo("github", "adminGitHub");

    public static final ProfileTo userProfile = new ProfileTo(1L, Set.of("deadline","overdue","assigned"), Set.of(mobile, website, skype));

    public static User getNew() {
        return new User(null, "new@gmail.com", "newPassword", "newFirstName", "newLastName", "newDisplayName", Role.DEV);
    }

    public static User getUpdated() {
        return new User(USER_ID, USER_MAIL, "updatedPassword", "updatedFirstName", "updatedLastName",
                "updatedDisplayName", Role.DEV, Role.ADMIN);
    }

    public static ProfileTo getProfileToUpdated() {
        return new ProfileTo(1L, Set.of("deadline","overdue","assigned"), Set.of(mobile, website, skype, github));
    }


    public static <T> String jsonWithPassword(T user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
