package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ProfileTo;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILETO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class, "mailNotifications", "contacts");
}
