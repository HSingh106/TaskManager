package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the persistence layer responsible for interactions with the postgreSQL database
 * for task entities
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
