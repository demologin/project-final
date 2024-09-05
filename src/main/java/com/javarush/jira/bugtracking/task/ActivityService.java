package com.javarush.jira.bugtracking.task;

import com.javarush.jira.bugtracking.Handlers;
import com.javarush.jira.bugtracking.task.to.ActivityTo;
import com.javarush.jira.common.error.DataConflictException;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.javarush.jira.bugtracking.task.TaskUtil.getLatestValue;

@Service
@RequiredArgsConstructor
public class ActivityService {
    public static final String STATUS_READY_FOR_REVIEW = "ready_for_review";
    public static final String STATUS_IN_PROGRESS = "in_progress";
    public static final String STATUS_DONE = "done";
    public static final String TIME_WITH_DAYS_FORMAT = "%d d %d h %d m %d s";
    private final TaskRepository taskRepository;

    private final Handlers.ActivityHandler handler;
    private final ActivityRepository activityRepository;

    private static void checkBelong(HasAuthorId activity) {
        if (activity.getAuthorId() != AuthUser.authId()) {
            throw new DataConflictException("Activity " + activity.getId() + " doesn't belong to " + AuthUser.get());
        }
    }

    @Transactional
    public Activity create(ActivityTo activityTo) {
        checkBelong(activityTo);
        Task task = taskRepository.getExisted(activityTo.getTaskId());
        if (activityTo.getStatusCode() != null) {
            task.checkAndSetStatusCode(activityTo.getStatusCode());
        }
        if (activityTo.getTypeCode() != null) {
            task.setTypeCode(activityTo.getTypeCode());
        }
        return handler.createFromTo(activityTo);
    }

    @Transactional
    public void update(ActivityTo activityTo, long id) {
        checkBelong(handler.getRepository().getExisted(activityTo.getId()));
        handler.updateFromTo(activityTo, id);
        updateTaskIfRequired(activityTo.getTaskId(), activityTo.getStatusCode(), activityTo.getTypeCode());
    }

    @Transactional
    public void delete(long id) {
        Activity activity = handler.getRepository().getExisted(id);
        checkBelong(activity);
        handler.delete(activity.id());
        updateTaskIfRequired(activity.getTaskId(), activity.getStatusCode(), activity.getTypeCode());
    }

    private void updateTaskIfRequired(long taskId, String activityStatus, String activityType) {
        if (activityStatus != null || activityType != null) {
            Task task = taskRepository.getExisted(taskId);
            List<Activity> activities = handler.getRepository().findAllByTaskIdOrderByUpdatedDesc(task.id());
            if (activityStatus != null) {
                String latestStatus = getLatestValue(activities, Activity::getStatusCode);
                if (latestStatus == null) {
                    throw new DataConflictException("Primary activity cannot be delete or update with null values");
                }
                task.setStatusCode(latestStatus);
            }
            if (activityType != null) {
                String latestType = getLatestValue(activities, Activity::getTypeCode);
                if (latestType == null) {
                    throw new DataConflictException("Primary activity cannot be delete or update with null values");
                }
                task.setTypeCode(latestType);
            }
        }
    }

    public String getTimeSpentOnWork(long taskId) {
        return getTimeBetweenStatus(taskId, STATUS_IN_PROGRESS, STATUS_READY_FOR_REVIEW);
    }

    public String getTimeSpentOnTest(long taskId) {
        return getTimeBetweenStatus(taskId, STATUS_READY_FOR_REVIEW, STATUS_DONE);
    }

    private String getTimeBetweenStatus(long taskId, String startStatus, String endStatus) {
        taskRepository.getExisted(taskId);
        LocalDateTime start = getActivityUpdatedByTaskIdAndStatus(taskId, startStatus);
        LocalDateTime end = getActivityUpdatedByTaskIdAndStatus(taskId, endStatus);
        Duration duration = Duration.between(start, end);
        return TIME_WITH_DAYS_FORMAT.formatted(duration.toDaysPart(),
                duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart()
        );
    }

    private LocalDateTime getActivityUpdatedByTaskIdAndStatus(long taskId, String status) {
        return activityRepository
                .findAllByTaskIdOrderByUpdatedDesc(taskId)
                .stream()
                .filter(activity -> activity.getUpdated() != null)
                .filter(activity -> status.equals(activity.getStatusCode()))
                .findFirst()
                .orElseThrow(
                        () -> new NotFoundException("Task " + taskId + " doesn't have activity with status "+ status)
                )
                .getUpdated();
    }
}
