package com.example.TaskManager.mappers.implementation;

import com.example.TaskManager.Model.DTO.TaskDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * represents a mapper meant for tasks and taskDTO's
 */
@Component
public class TaskMapper implements Mapper<Task, TaskDTO> {

    ModelMapper modelMapper = new ModelMapper();

    /**
     * constructor injection to inject necessary dependency
     * @param modelMapper represents mapper for mapping information from one object to another
     */
    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * map from a task object to its corresponding DTO
     * @param task object to be mapped over to a DTO
     * @return a TaskDTO containing the task's information
     */
    @Override
    public TaskDTO mapTo(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    /**
     * map from taskDTO to task object
     * @param taskDTO taskDTO to be mapped over
     * @return the newly made task
     */
    @Override
    public Task mapFrom(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
}
