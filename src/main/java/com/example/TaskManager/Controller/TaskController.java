package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.TaskDTO;
import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Service.TaskService;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private TaskService taskService;

    private Mapper<Task, TaskDTO> taskMapper;

    public TaskController(TaskService taskService, Mapper<Task, TaskDTO> taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(path = "/tasks/{id}")
    public ResponseEntity<Task> addTask(@PathVariable("id")  Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapFrom(taskDTO);
        Task createdTask = taskService.save(id, task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }



}
