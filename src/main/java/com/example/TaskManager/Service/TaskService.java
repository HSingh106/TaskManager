package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.Task;


import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task save(Long id, Task task);

    Optional<Task> findOne(Long id);

    List<Task> findAll();

    void delete(Long id);

    boolean existsById(Long id);

    Task partialUpdate(Long id, Task task);
}
