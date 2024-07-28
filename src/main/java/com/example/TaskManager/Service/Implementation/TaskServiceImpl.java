package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Exceptions.TaskWithNameDoesNotExistException;
import com.example.TaskManager.Exceptions.UserNotFoundException;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.TaskManager.Model.Entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

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
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user with associated review not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public Task findOne(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }


    @Override
    public Page<Task> findAll(Pageable page, Long id) {

        List<Task> tasks = userRepository.findById(id).get().getTasks();

        return new PageImpl<>(tasks, page, tasks.size());

    }

    @Override
    public Page<Task> findAllUsersWithName(Pageable pageable, Long userId, String name) {
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
        return new PageImpl<>(foundTasks, pageable, foundTasks.size());
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
