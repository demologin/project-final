package com.javarush.jira.bugtracking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

//TODO: Task 6. Adding tags to the tasks.
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/task/tag")
public class TaskTagController {

    private final TaskService taskService;

    @PostMapping("/{id}")
    public String assignTags(@PathVariable Long id, @RequestBody String[] tags) {
        taskService.addTags(id, Set.of(tags));
        return "redirect:/";
    }
}
