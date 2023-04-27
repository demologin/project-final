package com.javarush.jira.bugtracking.to;

import com.javarush.jira.common.to.BaseTo;
import com.javarush.jira.common.util.validation.Code;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserBelongTo extends BaseTo {
    //TODO 7.subscribe task
    @NotNull
    ObjectType objectType;
    @NotNull
    Long userId;
    @Code
    String userTypeCode;

}
