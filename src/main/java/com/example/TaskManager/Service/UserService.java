package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findOne(Long id);

    Page<User> findAll(Pageable pageable);

    void delete(Long id);

    boolean existsById(Long id);

    User partialUpdate(Long id, User user);
}
