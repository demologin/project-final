package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.UserBelongMapper;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.UserBelongTo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
//TODO notifications for watching objects functionality
@Service
@AllArgsConstructor
public class UserBelongService {
    UserBelongRepository repository;
    UserBelongMapper mapper;

    public List<UserBelongTo> getToByUserId(Long id) {
        return mapper.toToList(repository.getUserBelongByUserId(id));
    }

    public List<UserBelongTo> getToByObjectId(Long id) {
        return mapper.toToList(repository.getUserBelongByObjectId(id));
    }

    public List<UserBelongTo> getToByRole(TaskUserRole role) {
        return mapper.toToList((repository.getUserBelongByTaskUserRole(role)));
    }
}
