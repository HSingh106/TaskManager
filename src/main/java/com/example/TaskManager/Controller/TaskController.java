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

/**
 * Represents endpoints for creating, reading, updating, and deleting
 * tasks of users
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    private Mapper<Task, TaskDTO> taskMapper;

    /**
     *
     * @param taskService allows access to repository functionality without exposing repository layer directly
     * @param taskMapper removes filler code and facilites the use of DTOs for request bodies
     */
    public TaskController(TaskService taskService, Mapper<Task, TaskDTO> taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /**
     * Create endpoint for creating a task
     * @param id represents user's id
     * @param taskDTO the information to be stored in task object
     * @return a response entity containing the created task and HttpStatus.CREATED
     */
    @PostMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> addTask(@PathVariable("id")  Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapFrom(taskDTO);
        Task createdTask = taskService.save(id, task);
        return new ResponseEntity<>(taskMapper.mapTo(createdTask), HttpStatus.CREATED);
    }

    /**
     * Get endpoint for finding a single task with a certain id
     * @param id represents the id of the task to be found
     * @return
     */
    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskId") Long id){
        return new ResponseEntity<>(taskMapper.mapTo(taskService.findOne(id)), HttpStatus.OK);
    }


    /**
     * Get endpoint for getting all tasks of a certain user
     * @param userId represents the user's id
     * @param pageable pageable object that is injected by spring for us
     * @return a page object containing all the contents of the user's tasks
     */
    @GetMapping(path = "/users/{id}")
    public Page<TaskDTO> getAllTasksForUser(@PathVariable("id") Long userId, Pageable pageable){
        Page<Task> tasks = taskService.findAll(pageable,userId);
        return tasks.map(taskMapper::mapTo);
    }

    /**
     * Get endpoint for getting all tasks of a certain user with a certain name
     * @param userId the id representing which user to fetch the tasks from
     * @param name the name of the tasks to be searched for
     * @param pageable pageable that is injected by spring
     * @return a page object containing the information of the found task objects matching the criteria
     */
    @GetMapping(path = "/users/{id}/{name}")
    public Page<TaskDTO> getAllTasksWithName(@PathVariable("id") Long userId, @PathVariable("name") String name, Pageable pageable){
        Page<Task> tasks = taskService.findAllUsersWithName(pageable, userId, name);
        return tasks.map(taskMapper::mapTo);
    }

    /**
     * Delete endpoint for removing a certain task
     * @param id represents the id of the task to be removed
     * @return a response entity with an Http status of no content
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Long id) {
        if(!taskService.existsById(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Patch endpoint for a task object
     * @param id represents the id of the task object
     * @param task represents the information to be updated within the existing task
     * @return a response entity containing the newly updated task
     */
    @PatchMapping("/PartialUpdate/{id}")
    public ResponseEntity<Task> partialUpdateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.partialUpdate(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Put endpoint for completly updating a task
     * @param taskId represents the id of the task to be updated
     * @param TaskDTO represents the contents to be swapped over
     * @return a response entity containg the saved task and a status of ok
     */
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
