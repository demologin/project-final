package com.javarush.jira.bugtracking.task;

import com.javarush.jira.bugtracking.Handlers;
import com.javarush.jira.bugtracking.UserBelong;
import com.javarush.jira.bugtracking.UserBelongRepository;
import com.javarush.jira.bugtracking.sprint.Sprint;
import com.javarush.jira.bugtracking.sprint.SprintRepository;
import com.javarush.jira.bugtracking.task.mapper.TaskExtMapper;
import com.javarush.jira.bugtracking.task.mapper.TaskFullMapper;
import com.javarush.jira.bugtracking.task.to.RangeTo;
import com.javarush.jira.bugtracking.task.to.TaskToExt;
import com.javarush.jira.bugtracking.task.to.TaskToFull;
import com.javarush.jira.common.error.DataConflictException;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.common.util.Util;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.ref.RefType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.javarush.jira.bugtracking.ObjectType.TASK;
import static com.javarush.jira.bugtracking.task.TaskUtil.fillExtraFields;
import static com.javarush.jira.bugtracking.task.TaskUtil.makeActivity;
import static com.javarush.jira.ref.ReferenceService.getRefTo;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class TaskService {
    static final String CANNOT_ASSIGN = "Cannot assign as %s to task with status=%s";
    static final String CANNOT_UN_ASSIGN = "Cannot unassign as %s from task with status=%s";

    public static final String STATUS_CODE_IN_PROGRESS = "in_progress";
    public static final String STATUS_CODE_READY_FOR_REVIEW = "ready_for_review";
    public static final String STATUS_CODE_DONE = "done";
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final Handlers.TaskExtHandler handler;
    private final Handlers.ActivityHandler activityHandler;
    private final TaskFullMapper fullMapper;
    private final SprintRepository sprintRepository;
    private final TaskExtMapper extMapper;
    private final UserBelongRepository userBelongRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void changeStatus(long taskId, String statusCode) {
        Assert.notNull(statusCode, "statusCode must not be null");
        Task task = handler.getRepository().getExisted(taskId);
        if (!statusCode.equals(task.getStatusCode())) {
            task.checkAndSetStatusCode(statusCode);
            Activity statusChangedActivity = new Activity(null, taskId, AuthUser.authId());
            statusChangedActivity.setStatusCode(statusCode);
            activityHandler.create(statusChangedActivity);
            String userType = getRefTo(RefType.TASK_STATUS, statusCode).getAux(1);
            if (userType != null) {
                handler.createUserBelong(taskId, TASK, AuthUser.authId(), userType);
            }
        }
    }

    @Transactional
    public void changeSprint(long taskId, Long sprintId) {
        Task task = handler.getRepository().getExisted(taskId);
        if (task.getParentId() != null) {
            throw new DataConflictException("Can't change subtask sprint");
        }
        if (sprintId != null) {
            Sprint sprint = sprintRepository.getExisted(sprintId);
            if (sprint.getProjectId() != task.getProjectId()) {
                throw new DataConflictException("Target sprint must belong to the same project");
            }
        }
        handler.getRepository().setTaskAndSubTasksSprint(taskId, sprintId);
    }

    @Transactional
    public Task create(TaskToExt taskTo) {
        Task created = handler.createWithBelong(taskTo, TASK, "task_author");
        activityHandler.create(makeActivity(created.id(), taskTo));
        return created;
    }

    @Transactional
    public void update(TaskToExt taskTo, long id) {
        if (!taskTo.equals(get(taskTo.id()))) {
            handler.updateFromTo(taskTo, id);
            activityHandler.create(makeActivity(id, taskTo));
        }
    }

    @Transactional
    public void addTags(Set<String> tags, long id) {
        Task task = taskRepository.getExisted(id);
        task.getTags().addAll(tags);
        taskRepository.save(task);
    }

    @Transactional
    public void replaceTags(Set<String> tags, long id) {
        Task task = taskRepository.getExisted(id);
        task.setTags(tags);
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTags(long id) {
        Optional<Task> optionalTask = taskRepository.findFullById(id);
        Task task = optionalTask.orElseThrow();
        task.getTags().clear();
        taskRepository.save(task);
    }


    public TaskToFull get(long id) {
        Task task = Util.checkExist(id, handler.getRepository().findFullById(id));
        TaskToFull taskToFull = fullMapper.toTo(task);
        List<Activity> activities = activityHandler.getRepository().findAllByTaskIdOrderByUpdatedDesc(id);
        fillExtraFields(taskToFull, activities);
        taskToFull.setActivityTos(activityHandler.getMapper().toToList(activities));
        return taskToFull;
    }

    public TaskToExt getNewWithSprint(long sprintId) {
        Sprint sprint = sprintRepository.getExisted(sprintId);
        Task newTask = new Task();
        newTask.setSprintId(sprintId);
        newTask.setProjectId(sprint.getProjectId());
        return extMapper.toTo(newTask);
    }

    public TaskToExt getNewWithProject(long projectId) {
        Task newTask = new Task();
        newTask.setProjectId(projectId);
        return extMapper.toTo(newTask);
    }

    public TaskToExt getNewWithParent(long parentId) {
        Task parent = handler.getRepository().getExisted(parentId);
        Task newTask = new Task();
        newTask.setParentId(parentId);
        newTask.setSprintId(parent.getSprintId());
        newTask.setProjectId(parent.getProjectId());
        return extMapper.toTo(newTask);
    }

    public void assign(long id, String userType, long userId) {
        checkAssignmentActionPossible(id, userType, true);
        handler.createUserBelong(id, TASK, userId, userType);
    }

    @Transactional
    public void unAssign(long id, String userType, long userId) {
        checkAssignmentActionPossible(id, userType, false);
        UserBelong assignment = userBelongRepository.findActiveAssignment(id, TASK, userId, userType)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Not found assignment with userType=%s for task {%d} for user {%d}", userType, id, userId)));
        assignment.setEndpoint(LocalDateTime.now());
    }

    private void checkAssignmentActionPossible(long id, String userType, boolean assign) {
        Assert.notNull(userType, "userType must not be null");
        Task task = handler.getRepository().getExisted(id);
        String possibleUserType = getRefTo(RefType.TASK_STATUS, task.getStatusCode()).getAux(1);
        if (!userType.equals(possibleUserType)) {
            throw new DataConflictException(String.format(assign ? CANNOT_ASSIGN : CANNOT_UN_ASSIGN, userType, task.getStatusCode()));
        }
    }

    public Set<String> getTags(long id) {
        Task task = taskRepository.getTaskWithTagsById(id);
        return task.getTags();
    }
    @Transactional
    public TaskToFull getTaskWithLastWorkAndTestRange(TaskToFull taskTo) {
        getTaskWithLastRange(taskTo, STATUS_CODE_IN_PROGRESS, STATUS_CODE_READY_FOR_REVIEW);
        getTaskWithLastRange(taskTo, STATUS_CODE_READY_FOR_REVIEW, STATUS_CODE_DONE);
        return taskTo;
    }

    private void getTaskWithLastRange(TaskToFull taskTo, String statusCodeStartRange, String statusCodeEndRange) {
        ActivityRepository activityRepository = activityHandler.getRepository();
        Optional<Activity> optionalStartRangePoint =
                activityRepository.findAllByTaskIdAndStatusCodeOrderByUpdatedDesc(
                        taskTo.getId(), statusCodeStartRange).findFirst();
        Optional<Activity> optionalEndRangePoint =
                activityRepository.findAllByTaskIdAndStatusCodeOrderByUpdatedDesc(
                        taskTo.getId(), statusCodeEndRange).findFirst();

        if (optionalStartRangePoint.isEmpty() || optionalEndRangePoint.isEmpty()) {
            return;
        }

        LocalDateTime startRangePoint = optionalStartRangePoint.get().getUpdated();
        LocalDateTime endRangePoint = optionalEndRangePoint.get().getUpdated();

        if (isNull(startRangePoint) || isNull(endRangePoint)) {
            return;
        }

        if (endRangePoint.isBefore(startRangePoint)) {
            return;
        }

        Period taskWorkDate = Period.between(startRangePoint.toLocalDate(), endRangePoint.toLocalDate());
        Duration taskWorkTime = Duration.between(startRangePoint.toLocalTime(), endRangePoint.toLocalTime());

        RangeTo taskWorkRange = new RangeTo(taskWorkDate, taskWorkTime);
        if(statusCodeStartRange.equals(STATUS_CODE_IN_PROGRESS) && statusCodeEndRange.equals(STATUS_CODE_READY_FOR_REVIEW)){
            taskTo.setWorkRange(taskWorkRange);
        } else if(statusCodeStartRange.equals(STATUS_CODE_READY_FOR_REVIEW) && statusCodeEndRange.equals(STATUS_CODE_DONE)){
            taskTo.setTestRange(taskWorkRange);
        } else {
            log.error("Illegal statusCode arguments: {}, {}", statusCodeStartRange, statusCodeEndRange);
            throw new IllegalArgumentException("Illegal statusCode arguments: %s, %s".formatted(statusCodeStartRange, statusCodeEndRange));
        }
    }
}
