package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Model.Entities.Role;
import com.example.TaskManager.Repository.RoleRepository;
import com.example.TaskManager.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName("name");
    }

}
