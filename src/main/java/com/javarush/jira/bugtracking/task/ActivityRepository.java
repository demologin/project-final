package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ActivityRepository extends BaseRepository<Activity> {
    @Query("SELECT a FROM Activity a JOIN FETCH a.author WHERE a.taskId =:taskId ORDER BY a.updated DESC")
    List<Activity> findAllByTaskIdOrderByUpdatedDesc(long taskId);

    @Query("SELECT a FROM Activity a JOIN FETCH a.author WHERE a.taskId =:taskId AND a.comment IS NOT NULL ORDER BY a.updated DESC")
    List<Activity> findAllComments(long taskId);

    // TODO p8 - counting the time how much the task was in work and testing
    @Query("SELECT MIN(a.updated) FROM Activity a WHERE a.taskId = :taskId AND a.statusCode = :statusCode")
    Optional<LocalDateTime> getStatusChangeTime(@Param("taskId") Long taskId, @Param("statusCode") String statusCode);
}
