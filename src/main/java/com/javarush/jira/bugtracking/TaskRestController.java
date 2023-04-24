package com.javarush.jira.bugtracking;


import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

//TODO add Tags adding functionality

@RestController
@RequestMapping(value = TaskRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class TaskRestController {
    static final String REST_URL = "/api/task";

    private TaskRepository repository;

    @PutMapping("/{id}/add_tag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void addTag(@PathVariable Long id, @RequestParam String tag) {
        log.debug("add tag tag={} to Task with id={}", tag, id);
        Task task = repository.getExisted(id);
        task.getTags().add(tag);
        repository.save(task);
    }

    @PutMapping("/{id}/delete_tag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteTag(@PathVariable Long id, @RequestParam String tag) {
        log.debug("delete tag tag={} from Task with id={}", tag, id);
        Task task = repository.getExisted(id);
        Set<String> tags = task.getTags();
        if (tags.contains(tag)){
            task.getTags().remove(tag);
            repository.save(task);
        }
    }
}
