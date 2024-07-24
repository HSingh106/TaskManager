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
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;

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


    @GetMapping(path = "/users/{id}/tasks")
    public List<TaskDTO> getAllTasksForUser(@PathVariable("id") Long userId){
        List<Task> tasks = taskService.findAll(userId);
        return tasks.stream()
                .map(taskMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/{id}/tasks/{name}")
    public List<TaskDTO> getAllTasksWithName(@PathVariable("id") Long userId, @PathVariable("name") String name){
        List<Task> tasks = taskService.findAllUsersWithName(userId, name);
        return tasks.stream()
                .map(taskMapper::mapTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/tasks/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/task/PartialUpdate/{id}")
    public ResponseEntity<Task> partialUpdateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.partialUpdate(id, task);
        return ResponseEntity.ok(updatedTask);
    }



}
