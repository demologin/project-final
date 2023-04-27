package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskRepository extends BaseRepository<Task> {
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.sprint LEFT JOIN FETCH t.activities")
    List<Task> getAll();

    // TODO 12 - add backlog
    @Query(value = "select t from Task t " +
            "left join fetch t.sprint " +
            "left join fetch t.activities " +
            "left join fetch t.project " +
            "where t.sprint is null",
    countQuery = "select t from Task t where t.sprint is null")
    Page<Task> findTasksWithNullSprints(Pageable pageable);
}