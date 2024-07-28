package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.mappers.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    private Mapper<UserEntity, UserDTO> userMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDTO> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserEntity user = userMapper.mapFrom(userDTO);
        UserEntity savedUser = userService.save(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        Optional<UserEntity> userFound = userService.findOne(id);
        return userFound.map(user -> {
            UserDTO userDTO = userMapper.mapTo(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users/all")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> userFound = userService.findAll(pageable);
        return userFound.map(userMapper::mapTo);
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/user/Update/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        if(!userService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity user = userMapper.mapFrom(userDTO);
        UserEntity updatedUser = userService.partialUpdate(id, user);
        return ResponseEntity.ok(userMapper.mapTo(updatedUser));
    }

    @PutMapping(path = "/user/Complete/{id}")
    public ResponseEntity<UserDTO> fullUpdateUser(@PathVariable("id") Long userId, @RequestBody UserDTO userDTO){
        if(!userService.existsById(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       // userDTO.setId(userId);
        UserEntity user = userMapper.mapFrom(userDTO);
        UserEntity savedUser = userService.save(user);
        return new ResponseEntity<>(
                userMapper.mapTo(savedUser),
                HttpStatus.OK);
    }



}
