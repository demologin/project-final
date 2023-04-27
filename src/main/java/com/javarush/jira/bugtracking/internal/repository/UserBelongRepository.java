package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.TaskUserRole;
import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.common.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//TODO notifications for watching objects functionality
@Transactional(readOnly = true)
public interface UserBelongRepository extends BaseRepository<UserBelong> {
    List<UserBelong> getUserBelongByUserId(Long userId);
    List<UserBelong> getUserBelongByObjectId(Long objectId);

    List<UserBelong> getUserBelongByTaskUserRole(TaskUserRole role);

}
