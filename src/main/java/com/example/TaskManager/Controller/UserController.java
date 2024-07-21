package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.mappers.Mapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    private Mapper<User, UserDTO> userMapper;

    public UserController(UserService userService, Mapper<User, UserDTO> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapFrom(userDTO);
        User savedUser = userService.save(user);
        return userMapper.mapTo(savedUser);
    }



}
