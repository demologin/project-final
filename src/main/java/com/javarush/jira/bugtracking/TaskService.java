package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.internal.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private UserBelongRepository userBelongRepository;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    // TODO 12 - add backlog
    public Page<TaskTo> getTasksWithNullSprints(int page, int size) {
        return repository.findTasksWithNullSprints(PageRequest.of(page - 1, size)).map(mapper::toTo);
    }

    // TODO 6 - add tags
    public void addTaskTags(Long taskId, Set<String> tags) {
        Task task = repository.getExisted(taskId);
        task.getTags().addAll(tags);
        repository.save(task);
    }

    // TODO 7 - add user subscribe
    public void addUserToTask(Long taskId, Long userId) {
        if (taskRepository.existsById(taskId) && userRepository.existsById(userId)) {
            UserBelong uBelong = new UserBelong();
            uBelong.setUserTypeCode("user");
            uBelong.setUserId(userId);
            uBelong.setObjectId(taskId);
            uBelong.setObjectType(ObjectType.TASK);
            Optional<UserBelong> belong = userBelongRepository.findOne(Example.of(uBelong));
            if (belong.isEmpty()) {
                userBelongRepository.save(uBelong);
            }
        }
    }
}
