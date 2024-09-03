package com.javarush.jira.bugtracking.task;

import lombok.Getter;

@Getter
public enum TaskStatusTrack {
    IN_PROGRESS("in_progress"),
    READY_FOR_REVIEW("ready_for_review"),
    DONE("done");

    private final String name;

    TaskStatusTrack(String name) {
        this.name = name;
    }
}
