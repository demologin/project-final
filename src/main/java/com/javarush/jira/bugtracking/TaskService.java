package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    public TaskTo getById(long id){
        Task task = repository.getExisted(id);
        return mapper.toTo(task);
    }

    public void updateById(Long id, TaskTo taskToForm) {

        log.info("update {} with id={}", taskToForm, id);

        ValidationUtil.assureIdConsistent(taskToForm, id);
        Task dbTask = repository.getExisted(id);
        Task updatedTask = mapper.updateFromTo(dbTask, taskToForm);
        repository.save(updatedTask);
    }
}
