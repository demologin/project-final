package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.ProjectTo;
import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileUtil;

import java.time.LocalDateTime;
import java.util.Set;

public class UserBelongServiceTestData {

    public static final String USER_TYPE = "admin";

    public static final ProjectTo PROJECT_TO = new ProjectTo(
            2L,
            "PROJECT-1",
            true,
            "task tracker",
            "test project",
            "task tracker",
            null);

    public static final SprintTo SPRINT_TO = new SprintTo(
            1L,
            "Sprint-1",
            true,
            "planning",
            LocalDateTime.now(),
            null,
            PROJECT_TO);

}
