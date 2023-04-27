package com.javarush.jira.bugtracking.activity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//TODO created ActivityController
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("time")
public class ActivityController {

    private final ActivityService service;
//TODO method for calculate time in work for task
    @GetMapping("{taskId}")
    public String addTaskToUser(Model model, @PathVariable Long taskId){
        if (service.checkExistence(taskId)) {
            model.addAttribute("timeAtWork",service.getTimeForTask(taskId));
        }
        return "randomView";
    }
}
