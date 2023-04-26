package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.SprintTo;
import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        // !!!Оптимизировать обращение к базе!!!
        List<TaskTo> tasks = taskService.getAll();
        Map<SprintTo, List<TaskTo>> taskMap = tasks.stream()
                .filter(task -> Objects.nonNull(task.getSprint()))
                .collect(Collectors.groupingBy(TaskTo::getSprint));
        model.addAttribute("taskMap", taskMap);
        return "index";
    }
    // TODO Добавить новый функционал: добавления тегов к задаче. Фронт делать необязательно.
    @PostMapping("/tasks/{id}/tags")
    public String addTagToTask(@PathVariable("id") Long taskId, Set<String> tags) {
        taskService.addTagToTask(taskId, tags);
        return "redirect:/";
    }

    /*TODO Добавить возможность подписываться на задачи, которые не назначены на текущего пользователя.
           (Рассылку уведомлений/письма о смене статуса задачи делать не нужно).*/
    @PostMapping("/tasks/{tid}/user/{uid}")
    public String addTaskToUser(@PathVariable("tid") Long taskId,
                                @PathVariable("uid") Long userId) {
        taskService.addTaskToUser(taskId, userId);
        return "redirect:/";
    }

    // TODO Реализовать бэклог (backlog) – полный список задач (с пейджингом),
    @GetMapping("/tasks")
    public String getFreeTasks(Model model) {
        // !!!Оптимизировать обращение к базе!!!
        List<TaskTo> all = taskService.getAll();
        List<TaskTo> freeTasks = all.stream()
                .filter(task -> Objects.isNull(task.getSprint()))
                .toList();
        model.addAttribute("freeTasks", freeTasks);
        return "free-tasks";
    }

}
