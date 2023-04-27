package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping
public class DashboardUIController {

    private TaskService taskService;

    @GetMapping("/") // index page
    public String getAll(Model model) {
        List<TaskTo> tasks = taskService.getAll();
        Map<SprintTo, List<TaskTo>> taskMap = tasks.stream()
                .filter(taskTo -> taskTo.getSprint() != null)
                .collect(Collectors.groupingBy(TaskTo::getSprint));
        model.addAttribute("taskMap", taskMap);
        return "index";
    }

    // TODO 12 - add backlog
    @GetMapping("/backlog")
    public String getBacklog(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Page<TaskTo> taskPage = taskService.getTasksWithNullSprints(page, size);
        model.addAttribute("taskPage", taskPage);
        model.addAttribute("pageSize", size);
        return "backlog";
    }

    // TODO 6 - add tags
    @PostMapping("/tasks/{id}/tags")
    public String addTaskTag(@PathVariable("id") Long taskId, @RequestBody String[] tagsFrom) {
        Set<String> tags = Set.of(tagsFrom);
        taskService.addTaskTags(taskId, tags);
        return "redirect:/";
    }

    // TODO 7 - add user subscribe
    @PostMapping("/tasks/{id}/users/{userId}")
    public String addUserToTask(@PathVariable("id") Long taskId, @PathVariable("userId") Long userId) {
        taskService.addUserToTask(taskId, userId);
        return "redirect:/";
    }
}
