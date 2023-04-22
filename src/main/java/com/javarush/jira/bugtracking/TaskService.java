package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    //    TODO added method for adding tags
    public void addTag(long id, String tag) {
        Task task = repository.findById(id).get();
        Set<String> tags = task.getTags();
        tags.add(tag);
        task.setTags(tags);
        repository.saveAndFlush(task);
    }
//TODO method for check task existing
    public boolean checkExistence(long id) {
        return repository.findById(id).isPresent();
    }
}
