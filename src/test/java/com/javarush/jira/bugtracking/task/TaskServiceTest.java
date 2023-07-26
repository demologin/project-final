package com.javarush.jira.bugtracking.task;

import com.javarush.jira.AbstractServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// TODO task 8 - counting the time how much the task was in work and testing
@SpringBootTest
class TaskServiceTest extends AbstractServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private ActivityRepository activityRepository;

    @Test
    void calculateTimeInProgressTest() {
        Task task = new Task();
        task.setId(1L);
        LocalDateTime startTime = LocalDateTime.now().minusDays(3);
        LocalDateTime reviewTime = LocalDateTime.now().minusDays(1);

        when(activityRepository.getStatusChangeTime(task.getId(), "in_progress")).thenReturn(Optional.of(startTime));
        when(activityRepository.getStatusChangeTime(task.getId(), "ready_for_review")).thenReturn(Optional.of(reviewTime));

        Duration expected = Duration.between(startTime, reviewTime);
        Duration actual = taskService.calculateTimeInProgress(task);

        assertEquals(expected, actual);
    }

    @Test
    void calculateTimeInTestingTest() {
        Task task = new Task();
        task.setId(1L);
        LocalDateTime reviewTime = LocalDateTime.now().minusDays(1);
        LocalDateTime doneTime = LocalDateTime.now();

        when(activityRepository.getStatusChangeTime(task.getId(), "ready_for_review")).thenReturn(Optional.of(reviewTime));
        when(activityRepository.getStatusChangeTime(task.getId(), "done")).thenReturn(Optional.of(doneTime));

        Duration expected = Duration.between(reviewTime, doneTime);
        Duration actual = taskService.calculateTimeInTesting(task);

        assertEquals(expected, actual);
    }
}