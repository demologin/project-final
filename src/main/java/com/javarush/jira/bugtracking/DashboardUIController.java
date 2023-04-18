package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping
public class DashboardUIController {
    public static final String ROOT_URL = "/";
    public static final String BACKLOG_URL ="/backlog";
    private TaskService taskService;

    @GetMapping(DashboardUIController.ROOT_URL) // index page
    public String getAllSprint(Model model) {
        List<TaskTo> tasks = taskService.getAllSprints();
        Map<SprintTo, List<TaskTo>> taskMap = tasks.stream()
                .collect(Collectors.groupingBy(TaskTo::getSprint));
        model.addAttribute("taskMap", taskMap);
        return "index";
    }

    @GetMapping(DashboardUIController.BACKLOG_URL)
    public String getBacklog() {
        return "backlog";
    }
}
