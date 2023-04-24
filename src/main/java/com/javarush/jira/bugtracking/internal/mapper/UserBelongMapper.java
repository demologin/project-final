package com.javarush.jira.bugtracking.internal.mapper;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.to.UserBelongTo;
import com.javarush.jira.common.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBelongMapper extends BaseMapper<UserBelong, UserBelongTo> {
    @Override
    UserBelong toEntity(UserBelongTo to);

    @Override
    List<UserBelong> toEntityList(Collection<UserBelongTo> tos);

    @Override
    UserBelongTo toTo(UserBelong entity);

    @Override
    List<UserBelongTo> toToList(Collection<UserBelong> entities);
}
