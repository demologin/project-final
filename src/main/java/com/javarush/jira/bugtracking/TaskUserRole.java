package com.javarush.jira.bugtracking;

import lombok.AllArgsConstructor;
import lombok.Getter;

//TODO notifications for watching objects functionality
@AllArgsConstructor
@Getter
public enum TaskUserRole {
    ASSIGNEE,
    WATCHER,
    REPORTER
}
