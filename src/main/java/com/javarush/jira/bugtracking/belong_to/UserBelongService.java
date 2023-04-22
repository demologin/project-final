package com.javarush.jira.bugtracking.belong_to;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.login.Role;
import org.springframework.stereotype.Service;

import java.util.List;
//TODO created UserBelongService
@Service
public class UserBelongService {

    private final UserBelongRepository repository;

    public UserBelongService(UserBelongRepository repository) {
        this.repository = repository;
    }
//TODO add task to user
    public void addTaskForUser( long taskId,long currentUserId) {
        List<UserBelong> allBelongs = repository.findAll();
        List<Long> currentUserTasks = getCurrentUserTasks(currentUserId, allBelongs);
        boolean isCurrentTaskInWork = currentUserTasks.stream().anyMatch(n -> n.equals(taskId));
        if (!isCurrentTaskInWork) {
            UserBelong userBelong = new UserBelong(taskId,
                    ObjectType.TASK,
                    currentUserId,
                    Role.ADMIN.name().toLowerCase());
            repository.saveAndFlush(userBelong);
        }
    }

//    TODO method for find current user tasks
    private static List<Long> getCurrentUserTasks(Long id, List<UserBelong> allBelongs) {
        return allBelongs
                .stream()
                .filter(n -> n.getUserId().equals(id))
                .map(UserBelong::getObjectId)
                .toList();
    }
}
