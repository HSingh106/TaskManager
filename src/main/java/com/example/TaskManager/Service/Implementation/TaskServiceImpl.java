package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task save(Task task) {
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
