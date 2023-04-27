package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Contact;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileUtil;

import java.util.Set;

public class ProfileTestData {

    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class);


    public static final ProfileTo profileToUser = new ProfileTo(
            1L,
            ProfileUtil.maskToNotifications(49),
            Set.of(
                    new ContactTo("skype", "userSkype"),
                    new ContactTo("mobile", "+01234567890"),
                    new ContactTo("website", "user.com")
            ));

    public static ProfileTo getProfileToUserUpdated(){
        return new ProfileTo(1L,
                ProfileUtil.maskToNotifications(14),
                null);
    }

    public static ContactTo getTestContactTo(){
        return new ContactTo("youtube", "testChannel");
    }

    public static final ProfileTo profileToAdmin = new ProfileTo(
            2L,
            ProfileUtil.maskToNotifications(14),
            Set.of(
                    new ContactTo("github", "adminGitHub"),
                    new ContactTo("tg", "adminTg"),
                    new ContactTo("vk", "adminVk")
            ));

    public static final ProfileTo profileToGuest = new ProfileTo(
            3L,
            null,
            null);

}
