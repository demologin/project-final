package com.javarush.jira.bugtracking.task.to;

import com.javarush.jira.common.HasCode;
import com.javarush.jira.common.HasIdAndParentId;
import com.javarush.jira.common.to.TitleTo;
import com.javarush.jira.common.util.validation.Code;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.*;

@Getter
public class TaskTo extends TitleTo implements HasCode, HasIdAndParentId {
    @Code
    private final String typeCode;
    Long parentId;
    @NotNull
    Long projectId;
    Long sprintId;
    @Setter
    @Code
    private String statusCode;
    private final Set<String> tags = new HashSet<>();

    public TaskTo(Long id, String code, String title, String typeCode, String statusCode,
                  Long parentId, Long projectId, Long sprintId, Set<String> tags) {
        super(id, code, title);
        this.typeCode = typeCode;
        this.statusCode = statusCode;
        this.parentId = parentId;
        this.projectId = projectId;
        this.sprintId = sprintId;
        this.tags.addAll(requireNonNullElse(tags, Set.of()));
    }
}
