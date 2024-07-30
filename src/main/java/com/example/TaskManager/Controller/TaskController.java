package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.TaskDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Service.TaskService;
import com.example.TaskManager.mappers.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    private Mapper<Task, TaskDTO> taskMapper;

    public TaskController(TaskService taskService, Mapper<Task, TaskDTO> taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> addTask(@PathVariable("id")  Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapFrom(taskDTO);
        Task createdTask = taskService.save(id, task);
        return new ResponseEntity<>(taskMapper.mapTo(createdTask), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskId") Long id){
        return new ResponseEntity<>(taskMapper.mapTo(taskService.findOne(id)), HttpStatus.OK);
    }


    @GetMapping(path = "/users/{id}")
    public Page<TaskDTO> getAllTasksForUser(@PathVariable("id") Long userId, Pageable pageable){
        Page<Task> tasks = taskService.findAll(pageable,userId);
        return tasks.map(taskMapper::mapTo);
    }

    @GetMapping(path = "/users/{id}/{name}")
    public Page<TaskDTO> getAllTasksWithName(@PathVariable("id") Long userId, @PathVariable("name") String name, Pageable pageable){
        Page<Task> tasks = taskService.findAllUsersWithName(pageable, userId, name);
        return tasks.map(taskMapper::mapTo);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Long id) {
        if(!taskService.existsById(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/PartialUpdate/{id}")
    public ResponseEntity<Task> partialUpdateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.partialUpdate(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping(path = "/complete/{taskId}")
    public ResponseEntity<TaskDTO> fullUpdateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDTO TaskDTO){
        if(!taskService.existsById(taskId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskDTO.setId(taskId);
        Task task = taskMapper.mapFrom(TaskDTO);
        Task savedTask = taskService.save(taskService.findOne(taskId).getUser().getId(),task);
        return new ResponseEntity<>(
                taskMapper.mapTo(savedTask),
                HttpStatus.OK);
    }



}
