package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity user) {
        UserEntity changeUser = userRepository.findById(id).get();
        if(user.getPassword() != null){
            changeUser.setPassword(user.getPassword());
        }
        if(user.getUsername() != null){
            changeUser.setUsername(user.getUsername());
        }
        return userRepository.save(changeUser);
    }
}

