package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository repository, TaskMapper mapper,
                       TaskRepository taskRepository) {
        super(repository, mapper);
        this.taskRepository = taskRepository;
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    @Transactional
    public void updateTaskTags(Long id, TaskTo taskTo) {
        Task taskById = taskRepository.getTaskById(id);
        Set<String> tags = Stream.concat(
                        taskById.getTags().stream(),
                        taskTo.getTags().stream())
                .collect(Collectors.toSet());
        taskById.setTags(tags);
    }

    public TaskTo getTaskById(Long id) {
        return mapper.toTo(repository.getTaskById(id));
    }
}
