package com.javarush.jira.bugtracking.to;

import com.javarush.jira.common.Subscribable;
import com.javarush.jira.common.util.validation.Code;
import com.javarush.jira.common.util.validation.NoHtml;
import com.javarush.jira.common.util.validation.View;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class TaskTo extends NodeTo<TaskTo> implements Subscribable {
    @Code
    String typeCode;

    @Code
    String statusCode;

    @NoHtml
    String description;

    @NotNull(groups = {View.OnCreate.class})
    SprintTo sprint;

    @NotNull(groups = {View.OnCreate.class})
    ProjectTo project;

    @Nullable
    LocalDateTime updated;

    @Code
    String priorityCode;

    @Positive
    Integer estimate;

    Set<String> tags;

    List<ActivityTo> activities;

    public TaskTo(Long id, String title, boolean enabled, String typeCode, String statusCode, String description,
                  SprintTo sprint, ProjectTo project, LocalDateTime updated,
                  String priorityCode, Integer estimate, Set<String> tags, List<ActivityTo> activities, TaskTo parent) {
        super(id, title, enabled, parent);
        this.typeCode = typeCode;
        this.statusCode = statusCode;
        this.description = description;
        this.sprint = sprint;
        this.project = project;
        this.updated = updated;
        this.priorityCode = priorityCode;
        this.estimate = estimate;
        this.tags = tags;
        this.activities = activities;
    }

    public ObjectType getObjectType() {
        return ObjectType.TASK;
    }
}
