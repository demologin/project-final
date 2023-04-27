package com.javarush.jira.bugtracking.internal.model;

import com.javarush.jira.bugtracking.TaskUserRole;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.common.model.TimestampEntry;
import com.javarush.jira.common.util.validation.Code;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Link user with role to any object (Project, Task, Sprint)
@Entity
@Table(name = "user_belong",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"object_id", "object_type", "user_id", "user_type_code"}, name = "uk_user_belong")})
@Getter
@Setter
@NoArgsConstructor
public class UserBelong extends TimestampEntry {

    // no FK, manual check
    @Column(name = "object_id", nullable = false)
    @NotNull
    private Long objectId;

    @Column(name = "object_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private ObjectType objectType;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Long userId;

    // link to Reference.code with RefType.USER_TYPE
    @Code
    @Column(name = "user_type_code", nullable = false)
    private String userTypeCode;

    //TODO add user role for notifications
    @Column(name = "task_user_role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private TaskUserRole taskUserRole;
}
