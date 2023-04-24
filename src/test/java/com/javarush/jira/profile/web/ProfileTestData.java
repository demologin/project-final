package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<Profile> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Profile.class, "lastLogin", "lastFailedLogin", "mailNotifications");

    public static final MatcherFactory.Matcher<ProfileTo> TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class);

    public static final long PROFILE_ID = 1;
    public static final long NOT_FOUND = 100;


    public static final Profile profile = new Profile(PROFILE_ID);


    public static Profile getNew() {
        return new Profile();
    }

    public static Profile getUpdated() {
        return new Profile(PROFILE_ID);
    }
    public static <T> String jsonWithPassword(T profile, Long id) {
        return JsonUtil.writeAdditionProps(profile, "id", id);
    }

}
