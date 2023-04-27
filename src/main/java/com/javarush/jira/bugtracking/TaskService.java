package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

//TODO 6.add task tag service
@Service
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

    public TaskTo getByID(Long id){
        if(repository.findById(id).isPresent()){
            return mapper.toTo(repository.findById(id).get());
        }
        return null;
    }

    public TaskTo addTag(Long id, Set<String> tags){
        Task task = repository.getExisted(id);
        task.getTags().addAll(tags);
        return mapper.toTo(repository.save(task));

    }
}
