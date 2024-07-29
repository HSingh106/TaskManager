package com.example.TaskManager.Service;

import com.example.TaskManager.Model.Entities.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String name);
}
