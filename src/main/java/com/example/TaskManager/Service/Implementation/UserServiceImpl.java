package com.example.TaskManager.Service.Implementation;

import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        return
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public User partialUpdate(Long id, User user) {
        return null;
    }
}
