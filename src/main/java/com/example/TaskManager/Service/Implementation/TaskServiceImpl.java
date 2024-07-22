package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Exceptions.UserNotFoundException;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.TaskService;
import org.springframework.stereotype.Service;
import com.example.TaskManager.Model.Entities.User;

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
    public Optional<Task> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Task partialUpdate(Long id, Task task) {
        return null;
    }
}
