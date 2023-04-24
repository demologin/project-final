package com.javarush.jira.bugtracking.to;

import com.javarush.jira.bugtracking.TaskUserRole;
import com.javarush.jira.common.to.BaseTo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

//TODO notifications for watching objects functionality
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBelongTo extends BaseTo {
    @NotNull
    Long userId;

    @NotNull
    String userTypeCode;

    @NotNull
    Long objectId;
    @NotNull
    ObjectType objectType;

    @NotNull
    TaskUserRole taskUserRole;

    public UserBelongTo(Long id, Long userId, String userTypeCode, Long objectId, ObjectType objectType, TaskUserRole taskUserRole) {
        super(id);
        this.userId = userId;
        this.userTypeCode = userTypeCode;
        this.objectId = objectId;
        this.objectType = objectType;
        this.taskUserRole = taskUserRole;
    }
}
