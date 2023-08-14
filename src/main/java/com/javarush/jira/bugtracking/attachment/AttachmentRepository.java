package com.javarush.jira.bugtracking.attachment;

import com.javarush.jira.bugtracking.ObjectType;
import com.javarush.jira.common.BaseRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AttachmentRepository extends BaseRepository<Attachment> {

    @Query("SELECT a FROM Attachment a WHERE a.objectId =:objectId AND a.objectType =:objectType")
    List<Attachment> getAllForObject(long objectId, ObjectType objectType);
}
