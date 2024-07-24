package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.Task;


import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task save(Long id, Task task);

    Task findOne(Long taskId);

    List<Task> findAll(Long id);

    List<Task> findAllUsersWithName(Long userId, String name);

    void delete(Long id);

    boolean existsById(Long id);

    Task partialUpdate(Long id, Task task);
}
