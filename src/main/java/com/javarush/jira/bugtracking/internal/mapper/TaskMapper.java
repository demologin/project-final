package com.javarush.jira.bugtracking.internal.mapper;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.common.BaseMapper;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SprintMapper.class, ProjectMapper.class})
public interface TaskMapper extends BaseMapper<Task, TaskTo> {

    @Mapping(target = "enabled", expression = "java(task.isEnabled())")
    @Override
    TaskTo toTo(Task task);

    @Override
    Task toEntity(TaskTo taskTo);

    @Override
    List<TaskTo> toToList(Collection<Task> tasks);

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateFromTo(@MappingTarget Task task, TaskTo taskTo);

    @AfterMapping
    default void setTaskTags(@MappingTarget Task task, TaskTo taskTo){
        task.getTags().addAll(taskTo.getTags());
    }

}
