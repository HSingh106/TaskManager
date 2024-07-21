package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findOne(Long id);

    List<User> findAll();

    void delete(Long id);

    boolean existsById(Long id);

    User partialUpdate(Long id, User user);
}
