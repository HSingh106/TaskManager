package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;

    private Mapper<User, UserDTO> userMapper;

    public UserController(UserService userService, Mapper<User, UserDTO> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapFrom(userDTO);
        User savedUser = userService.save(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        Optional<User> userFound = userService.findOne(id);
        return userFound.map(user -> {
            UserDTO userDTO = userMapper.mapTo(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users/all")
    public List<UserDTO> getAllUsers() {
        List<User> userFound = userService.findAll();
        return userFound.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/user/PartialUpdate/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.partialUpdate(id, user);
        return ResponseEntity.ok(updatedUser);
    }



}
