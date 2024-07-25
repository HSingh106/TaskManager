package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task save(Long id, Task task);

    Task findOne(Long taskId);

    Page<Task> findAll(Pageable pageable, Long id);

    Page<Task> findAllUsersWithName(Pageable page, Long userId, String name);

    void delete(Long id);

    boolean existsById(Long id);

    Task partialUpdate(Long id, Task task);
}
