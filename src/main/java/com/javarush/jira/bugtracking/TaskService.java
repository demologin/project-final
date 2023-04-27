package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll()).stream()
                .filter(taskTo -> taskTo.getSprint() != null)
                .toList();
    }

    //TODO Add backlog with pagination
    public Page<TaskTo> getBacklog(Pageable pageable) {
        String statusCode = "backlog";
        return repository.getBacklogWithPagination(pageable, statusCode).map(mapper::toTo);
    }
}
