package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findOne(Long id);

    Page<UserEntity> findAll(Pageable pageable);

    void delete(Long id);

    boolean existsById(Long id);

    UserEntity partialUpdate(Long id, UserEntity user);
}
