package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }
    //TODO: Задача №7
    @Transactional
    public void setTagsFromId(int id, String tag){
        List<Task> taskList = repository.getAll();
        Task task = taskList.stream().filter(t -> t.getId() == id).findFirst().orElseThrow();
        Set<@Size(min = 2, max = 32) String> tags = task.getTags();
        tags.add(tag);
    }
}
