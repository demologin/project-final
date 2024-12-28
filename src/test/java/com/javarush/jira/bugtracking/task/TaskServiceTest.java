package com.javarush.jira.bugtracking.task;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.task.to.TaskTo;
import com.javarush.jira.bugtracking.task.to.TaskToExt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class TaskServiceTest extends AbstractControllerTest {
    static final LocalDateTime IN_PROGRESS_TIME = LocalDateTime.parse("2023-07-15T09:05:10");
    static final LocalDateTime READY_FOR_REVIEW_TIME = LocalDateTime.parse("2023-07-30T12:25:10");
    static final LocalDateTime DONE_TIME = LocalDateTime.parse("2023-08-01T14:05:10");
    TaskToExt TASK_TO = new TaskToExt(5L,"task-5", "task", null, "todo", null, null, null, null, null, 0, null);

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private TaskService taskService;

    @Test
    void getTaskInProgressTime() {
        long expected = Duration.between(IN_PROGRESS_TIME, READY_FOR_REVIEW_TIME).toMinutes();
        long actual = taskService.getTaskInProgressTime(TASK_TO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTaskInTestingTime() {
        long expected = Duration.between(READY_FOR_REVIEW_TIME, DONE_TIME).toMinutes();
        long actual = taskService.getTaskInTestingTime(TASK_TO);

        Assertions.assertEquals(expected, actual);
    }
}