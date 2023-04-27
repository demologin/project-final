package com.javarush.jira.bugtracking;


import com.javarush.jira.bugtracking.internal.mapper.UserBelongMapper;
import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;

import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.bugtracking.to.UserBelongTo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserBelongService {
    //TODO 7.subscribe task

    private final UserBelongRepository repository;

    private final UserBelongMapper mapper;

    public void subscribeFromTask(Long id, long userId) {
        UserBelongTo userBelongTo = UserBelongTo.builder()
                .userId(userId)
                .objectType(ObjectType.TASK)
                .userTypeCode("user")
                .build();
        repository.save(mapper.toEntity(userBelongTo));
    }



}
