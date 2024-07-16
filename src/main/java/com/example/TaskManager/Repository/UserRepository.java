package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Task;
import com.example.TaskManager.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the persistence layer where interactions with the PostgreSQL database
 * are managed with respect to user entities
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
