package com.javarush.jira.bugtracking.belong_to;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("tasks")
//TODO created UserBelongController
public class UserBelongToController {

    private final UserBelongService service;

//TODO method for adding task to user without any view
    @GetMapping("{taskId}/{userId}")
    public String addTaskToUser(@PathVariable Long taskId,@PathVariable Long userId){
        service.addTaskForUser(taskId,userId);
        return "randomView";
    }
}
