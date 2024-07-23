package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Exceptions.TaskWithNameDoesNotExistException;
import com.example.TaskManager.Exceptions.UserNotFoundException;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.TaskService;
import org.springframework.stereotype.Service;
import com.example.TaskManager.Model.Entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Task save(Long id,Task task) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user with associated review not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public Task findOne(Long userId, String name) {
        List<Task> tasks = userRepository.findById(userId).get().getTasks();
        Task foundTask = null;
        for (Task task : tasks) {
            if (task.getTaskName().equals(name)) {
                foundTask = task;
            }
        }
        if(foundTask == null){
            throw new TaskWithNameDoesNotExistException("Cannot find task with name");
        }
        return foundTask;
    }

    @Override
    public List<Task> findAll(Long id) {
        return userRepository.findById(id).get().getTasks();
    }

    @Override
    public List<Task> findAllUsersWithName(Long userId, String name) {
        List<Task> tasks = userRepository.findById(userId).get().getTasks();
        List<Task> foundTasks = new ArrayList<>();
        for(Task task: tasks){
            if(task.getTaskName().equals(name)){
                foundTasks.add(task);
            }
        }
        if(foundTasks.isEmpty()){
            throw new TaskWithNameDoesNotExistException("Task Does Not Exist");
        }
        return foundTasks;
    }


    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }

    @Override
    public Task partialUpdate(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskWithNameDoesNotExistException("Cannot find task"));

        if (task.getTaskName() != null) {
            existingTask.setTaskName(task.getTaskName());
        }
        if (task.getTaskDescription() != null) {
            existingTask.setTaskDescription(task.getTaskDescription());
        }
        if (task.getTaskStatus() != null) {
            existingTask.setTaskStatus(task.getTaskStatus());
        }
        if (task.getTaskType() != null) {
            existingTask.setTaskType(task.getTaskType());
        }
        if (task.getTaskStartDate() != null) {
            existingTask.setTaskStartDate(task.getTaskStartDate());
        }
        if (task.getTaskEndDate() != null) {
            existingTask.setTaskEndDate(task.getTaskEndDate());
        }

        return taskRepository.save(existingTask);
    }
}
