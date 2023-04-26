package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.error.ErrorMessageHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(path = TaskUIController.TASK_URL)
public class TaskUIController {
    static final String TASK_URL = "/ui/task";
    static final String REDIRECT_MAIN = "/";
    private static final String TASK_ERROR_ATTRIBUTE = "taskError";
    private final ErrorMessageHandler errorMessageHandler;
    private TaskService taskService;

    @GetMapping("/{id}")
    public String getTask(Model model, @PathVariable String id){
        TaskTo taskToById = taskService.getById(Long.parseLong(id));
        if(Objects.nonNull(taskToById)){
            model.addAttribute("selectedTask", taskToById);
            return "task";
        }
        return "redirect:%s".formatted(REDIRECT_MAIN);
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable String id,
                             @Valid TaskTo taskToForm,
                             BindingResult result, RedirectAttributes redirectAttrs){
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute(TASK_ERROR_ATTRIBUTE, errorMessageHandler.getErrorList(result));
            return "redirect:%s/%s".formatted(TASK_URL, id);
        }
        long parseLong = Long.parseLong(id);
        taskService.updateById(parseLong, taskToForm);
        return "redirect:%s/%s".formatted(TASK_URL, id);
    }

}
