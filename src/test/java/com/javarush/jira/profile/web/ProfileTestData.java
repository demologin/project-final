package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Contact;
import com.javarush.jira.profile.internal.Profile;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingEqualsComparator(
            ProfileTo.class);
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class, "contacts");


    public static final String USER_MAIL = "user@gmail.com";
    public static final long USER_ID = 1;

    public static final Profile profile = new Profile(USER_ID);

    private static final Contact mobile;

    private static final Contact website;
    private static final Contact skype;

    private static final Set<Contact> contacts;

    private static final long MAIL_NOTIFICATIONS = 49L;

    static {
        mobile = new Contact(USER_ID, "mobile", "+01234567890");
        website = new Contact(USER_ID,"website","user.com");
        skype = new Contact(USER_ID, "skype", "userSkype");
        contacts = new HashSet<>();
        contacts.add(mobile);
        contacts.add(website);
        contacts.add(skype);
        profile .setContacts(contacts);
        profile.setMailNotifications(MAIL_NOTIFICATIONS);
    }

    public static Profile getUpdated() {
        Profile updatedProfile = new Profile(USER_ID);
        updatedProfile.setContacts(contacts);
        updatedProfile.setMailNotifications(6L);
        return updatedProfile;
    }
}
