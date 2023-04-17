package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class,"mailNotifications","contacts");
    public static final MatcherFactory.Matcher<Profile> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Profile.class,"lastLogin","lastFailedLogin","contacts");

    public static <T> String createJson(T profileTo) {
        return JsonUtil.writeValue(profileTo);
    }
}
