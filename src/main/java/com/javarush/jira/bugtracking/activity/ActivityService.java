package com.javarush.jira.bugtracking.activity;

import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.bugtracking.internal.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

//TODO created ActivityService
@Service
public class ActivityService {

    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    //TODO method for create LocalTime for time task need to complete
    public LocalTime getTimeForTask(long taskId) {
        List<Activity> activity = repository.findAllByTaskId(taskId);
        String statusInProgress = "in progress";
        LocalDateTime startTime = getTime(activity, statusInProgress);
        String statusDone = "done";
        LocalDateTime endTime = getTime(activity, statusDone);
        int minutes = (int) ChronoUnit.MINUTES.between(endTime, startTime);
        int hours = (int) ChronoUnit.HOURS.between(endTime, startTime);
        return LocalTime.of(hours, minutes);
    }

    //    TODO check if task in work
    public boolean checkExistence(long taskId) {
        return repository.findAllByTaskId(taskId).size() > 0;
    }

    //TODO util method
    private LocalDateTime getTime(List<Activity> activity, String status) {
        return activity.stream()
                .filter(n -> n.getStatusCode().equals(status))
                .map(Activity::getUpdated)
                .findFirst().get();
    }

}
