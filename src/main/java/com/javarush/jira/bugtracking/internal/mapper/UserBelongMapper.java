package com.javarush.jira.bugtracking.internal.mapper;



import com.javarush.jira.bugtracking.internal.model.UserBelong;


import com.javarush.jira.bugtracking.to.UserBelongTo;
import com.javarush.jira.common.BaseMapper;
import org.mapstruct.Mapper;


import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBelongMapper  extends BaseMapper<UserBelong, UserBelongTo> {
    //TODO 7.subscribe task
    @Override
    UserBelongTo toTo(UserBelong userBelong);

    @Override
    UserBelong toEntity(UserBelongTo userBelongTo);

    @Override
    List<UserBelongTo> toToList(Collection<UserBelong> userBelongs);
}
