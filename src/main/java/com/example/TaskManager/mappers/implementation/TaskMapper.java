package com.example.TaskManager.mappers.implementation;

import com.example.TaskManager.Model.DTO.TaskDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements Mapper<Task, TaskDTO> {

    ModelMapper modelMapper = new ModelMapper();

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public TaskDTO mapTo(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    @Override
    public Task mapFrom(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
}
