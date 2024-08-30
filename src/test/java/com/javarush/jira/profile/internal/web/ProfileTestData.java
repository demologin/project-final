package com.javarush.jira.profile.internal.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;

import java.util.Collections;
import java.util.Set;

public class ProfileTestData {

    public static MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class, "user");

    public static final Long ADMIN_ID = 2L;
    public static final Long USER_ID = 1L;

    public static final String ASSIGNED = "assigned";
    public static final String THREE_DAYS_BEFORE_DEADLINE = "three_days_before_deadline";
    public static final String TWO_DAYS_BEFORE_DEADLINE = "two_days_before_deadline";
    public static final String ONE_DAY_BEFORE_DEADLINE = "one_day_before_deadline";
    public static final String DEADLINE = "deadline";
    public static final String OVERDUE = "overdue";

    public static final ContactTo USER_CONTACT_SKYPE = new ContactTo("skype", "userSkype");
    public static final ContactTo USER_CONTACT_MOBILE = new ContactTo("mobile", "+01234567890");
    public static final ContactTo USER_CONTACT_WEBSITE = new ContactTo("website", "user.com");

    public static final ContactTo USER_UPDATED_CONTACT_SKYPE = new ContactTo("skype", "newSkype");
    public static final ContactTo USER_UPDATED_CONTACT_MOBILE = new ContactTo("mobile", "+380987654321");
    public static final ContactTo USER_UPDATED_CONTACT_WEBSITE = new ContactTo("website", "new.com");
    public static final ContactTo USER_UPDATED_CONTACT_GITHUB = new ContactTo("github", "newGitHub");
    public static final ContactTo USER_UPDATED_CONTACT_TG = new ContactTo("tg", "newTg");
    public static final ContactTo USER_UPDATED_CONTACT_VK = new ContactTo("vk", "newVk");
    public static final ContactTo USER_UPDATED_CONTACT_LINKEDIN = new ContactTo("linkedin", "newLinkedin");

    public static final ContactTo ADMIN_CONTACT_GITHUB = new ContactTo("github", "adminGitHub");
    public static final ContactTo ADMIN_CONTACT_TG = new ContactTo("tg", "adminTg");
    public static final ContactTo ADMIN_CONTACT_VK = new ContactTo("vk", "adminVk");

    public static final ContactTo ADMIN_UPDATED_CONTACT_GITHUB = new ContactTo("github", "newAdminGitHub");
    public static final ContactTo ADMIN_UPDATED_CONTACT_TG = new ContactTo("tg", "newAdminTg");
    public static final ContactTo ADMIN_UPDATED_CONTACT_VK = new ContactTo("vk", "newAdminVk");
    public static final ContactTo ADMIN_UPDATED_CONTACT_SKYPE = new ContactTo("skype", "newAdminSkype");
    public static final ContactTo ADMIN_UPDATED_CONTACT_MOBILE = new ContactTo("mobile", "newAdminMobile");
    public static final ContactTo ADMIN_UPDATED_CONTACT_WEBSITE = new ContactTo("website", "newAdminWebsite");
    public static final ContactTo ADMIN_UPDATED_CONTACT_LINKEDIN = new ContactTo("linkedin", "newAdminLinkedin");


    public static final Set<String> USER_MAIL_NOTIFICATIONS = Set.of(
            ASSIGNED,
            OVERDUE,
            DEADLINE
    );

    public static final Set<ContactTo> USER_CONTACTS = Set.of(
            USER_CONTACT_SKYPE,
            USER_CONTACT_MOBILE,
            USER_CONTACT_WEBSITE
    );

    public static final Set<String> USER_UPDATED_MAIL_NOTIFICATIONS = Set.of(
            ASSIGNED,
            THREE_DAYS_BEFORE_DEADLINE,
            TWO_DAYS_BEFORE_DEADLINE,
            ONE_DAY_BEFORE_DEADLINE,
            DEADLINE,
            OVERDUE
    );

    public static final Set<ContactTo> USER_UPDATED_CONTACTS = Set.of(
            USER_UPDATED_CONTACT_SKYPE,
            USER_UPDATED_CONTACT_MOBILE,
            USER_UPDATED_CONTACT_WEBSITE,
            USER_UPDATED_CONTACT_GITHUB,
            USER_UPDATED_CONTACT_TG,
            USER_UPDATED_CONTACT_VK,
            USER_UPDATED_CONTACT_LINKEDIN
    );

    public static final Set<String> ADMIN_MAIL_NOTIFICATIONS = Set.of(
            TWO_DAYS_BEFORE_DEADLINE,
            ONE_DAY_BEFORE_DEADLINE,
            THREE_DAYS_BEFORE_DEADLINE
    );

    public static final Set<ContactTo> ADMIN_CONTACTS = Set.of(
            ADMIN_CONTACT_GITHUB,
            ADMIN_CONTACT_TG,
            ADMIN_CONTACT_VK
    );

    public static final Set<String> ADMIN_UPDATED_MAIL_NOTIFICATIONS = Set.of(
            THREE_DAYS_BEFORE_DEADLINE,
            TWO_DAYS_BEFORE_DEADLINE,
            ONE_DAY_BEFORE_DEADLINE,
            DEADLINE
    );

    public static final Set<ContactTo> ADMIN_UPDATED_CONTACTS = Set.of(
            ADMIN_UPDATED_CONTACT_GITHUB,
            ADMIN_UPDATED_CONTACT_TG,
            ADMIN_UPDATED_CONTACT_VK,
            ADMIN_UPDATED_CONTACT_SKYPE,
            ADMIN_UPDATED_CONTACT_MOBILE,
            ADMIN_UPDATED_CONTACT_WEBSITE,
            ADMIN_UPDATED_CONTACT_LINKEDIN
    );

    public static ProfileTo ADMIN_PROFILE_TO = new ProfileTo(
            ADMIN_ID,
            ADMIN_MAIL_NOTIFICATIONS,
            ADMIN_CONTACTS
    );

    public static ProfileTo USER_PROFILE_TO = new ProfileTo(
            USER_ID,
            USER_MAIL_NOTIFICATIONS,
            USER_CONTACTS
    );

    public static ProfileTo getUpdatedUserTo() {
        return new ProfileTo(
                USER_ID,
                USER_UPDATED_MAIL_NOTIFICATIONS,
                USER_UPDATED_CONTACTS
        );
    }

    public static ProfileTo getUpdatedAdminTo() {
        return new ProfileTo(
                ADMIN_ID,
                ADMIN_UPDATED_MAIL_NOTIFICATIONS,
                ADMIN_UPDATED_CONTACTS
        );
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
}
