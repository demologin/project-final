package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.common.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ActivityRepository extends BaseRepository<Activity> {
//TODO added method for find all Activities by TaskId
    List<Activity> findAllByTaskId(long id);
}
