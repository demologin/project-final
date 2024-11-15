package com.javarush.jira.profile.internal.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.model.Contact;
import com.javarush.jira.profile.internal.model.Profile;
import net.bytebuddy.implementation.bytecode.Throw;

import java.util.Collections;
import java.util.Set;

public class ProfileTestData {
    public static MatcherFactory.Matcher<Profile> PROFILE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Profile.class, "user");

    public static ProfileTo USER_PROFILE_TO = new ProfileTo(null,
            Set.of("assigned", "overdue", "deadline"),
            Set.of(new ContactTo("skype", "userSkype"),
                    new ContactTo("mobile", "+01234567890"),
                    new ContactTo("website", "user.com")));
    public static ProfileTo GUEST_PROFILE_EMPTY_TO = new ProfileTo(null,
            Set.of(),
            Set.of());

    public static ProfileTo getNewTo() {
        return new ProfileTo(null,
                Set.of("three_days_before_deadline", "two_days_before_deadline", "one_day_before_deadline"),
                Set.of(new ContactTo("tg", "guestTg")));
    }

    public static Profile getNew(long id) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setMailNotifications(14);
        profile.setContacts(Set.of(new Contact(id, "tg", "guestTg")));
        return profile;
    }

    public static ProfileTo getUpdatedTo() {
        return new ProfileTo(null,
                Set.of("assigned", "three_days_before_deadline", "two_days_before_deadline", "one_day_before_deadline", "deadline", "overdue"),
                Set.of(new ContactTo("skype", "newSkype"),
                        new ContactTo("mobile", "+380987654321"),
                        new ContactTo("website", "new.com"),
                        new ContactTo("github", "newGitHub"),
                        new ContactTo("tg", "newTg"),
                        new ContactTo("vk", "newVk"),
                        new ContactTo("linkedin", "newLinkedin")));
    }

    public static Profile getUpdated(long id) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setMailNotifications(63);
        profile.setContacts(Set.of(new Contact(id, "skype", "newSkype"),
                new Contact(id, "mobile", "+380987654321"),
                new Contact(id, "website", "new.com"),
                new Contact(id, "github", "newGitHub"),
                new Contact(id, "tg", "newTg"),
                new Contact(id, "vk", "newVk"),
                new Contact(id, "linkedin", "newLinkedin")));
        return profile;
    }

    public static ProfileTo getInvalidTo() {
        return new ProfileTo(null,
                Set.of(""),
                Set.of(new ContactTo("skype", "")));
    }

    public static ProfileTo getWithUnknownNotificationTo() {
        return new ProfileTo(null,
                Set.of("WrongNotification"),
                Collections.emptySet());
    }

    public static ProfileTo getWithUnknownContactTo() {
        return new ProfileTo(null,
                Collections.emptySet(),
                Set.of(new ContactTo("WrongContactCode", "contact")));
    }

    public static ProfileTo getWithContactHtmlUnsafeTo() {
        return new ProfileTo(null,
                Collections.emptySet(),
                Set.of(new ContactTo("tg", "<script>alert(123)</script>")));
    }

    public static ProfileTo getProfileToById(int id) {
        switch (id) {
            case 1 -> {
                return USER_PROFILE_TO;
            }
            case 2 -> {
                return GUEST_PROFILE_EMPTY_TO;
            }
            case 3 -> {
                return getNewTo();
            }
            case 4 -> {
                return getUpdatedTo();
            }
            case 5 -> {
                return getInvalidTo();
            }
            case 6 -> {
                return getWithUnknownNotificationTo();
            }
            case 7 -> {
                return getWithUnknownContactTo();
            }
            case 8 -> {
                return getWithContactHtmlUnsafeTo();
            }
            case 9 -> {
                return getProfileToById(1);
            }
            default -> {
                throw new RuntimeException("Not fitted id: " + id);
            }
        }
    }
}
