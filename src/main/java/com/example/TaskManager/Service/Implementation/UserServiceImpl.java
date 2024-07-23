package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(users::add);
        return users;
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
    public User partialUpdate(Long id, User user) {
        User changeUser = userRepository.findById(id).get();
        if(user.getPassword() != null){
            changeUser.setPassword(user.getPassword());
        }
        if(user.getUsername() != null){
            changeUser.setUsername(user.getUsername());
        }
        return userRepository.save(changeUser);
    }
}

