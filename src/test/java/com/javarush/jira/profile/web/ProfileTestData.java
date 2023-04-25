package com.javarush.jira.profile.web;
import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;

import java.util.Set;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class, "lastLogin", "mailNotifications", "contacts");

     public static final ProfileTo profileTo = new ProfileTo(1L, Set.of("deadline", "overdue", "assigned"),Set.of(new ContactTo("mobile", "+01234567890"),
            new ContactTo("website", "user.com"), new ContactTo("skype", "userSkype")));


    public static Profile getNew() {
        return new Profile();
    }

    public static ProfileTo getUpdated() {
        return new ProfileTo(1L, Set.of("deadline", "overdue", "assigned"),
                Set.of(new ContactTo("mobile", "+01234567890"),
                new ContactTo("website", "user.com"), new ContactTo("skype", "userSkype")));
    }
    public static <T> String jsonWithId(T user, String id) {
        return JsonUtil.writeAdditionProps(user, "Id", id);
    }

}
