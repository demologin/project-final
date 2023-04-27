package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(value = TaskController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    //TODO 6.add task tag controller
    public static final String REST_URL = "/api/task";

    private final TaskService taskService;

    private final UserBelongService userBelongService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskTo> getByID(@PathVariable Long id){
        log.info("get {}", id);
        return ResponseEntity.ok(taskService.getByID(id));
    }
    //TODO 7.subscribe task
    @PostMapping(value = "/{id}/subscribe")
    public void subscribeFromTask(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long id){
        log.info("subscribe {}", id);

        userBelongService.subscribeFromTask(id, authUser.id());

    }
    @PostMapping(value = "/{id}/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskTo> addTag(@PathVariable Long id, @RequestBody String[] tagsFrom) {
        log.info("update {},{}", id, tagsFrom);
        Set<String> tags = Set.of(tagsFrom);
        return ResponseEntity.ok(taskService.addTag(id, tags));
    }




}
