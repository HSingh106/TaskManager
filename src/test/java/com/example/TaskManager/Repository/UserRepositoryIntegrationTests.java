package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.TestDataUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryIntegrationTests(UserRepository ur){
        this.userRepository = ur;
    }

    /**
     * Tests to make sure save and recall via id operations work when
     * saving and recalling entities from a database one at a time.
     */
    @Test
    @Transactional
    public void UserRepository_TestSaveAndReturnUser_ReturnSavedUsers(){
        User user = TestDataUtil.createTestUserOne();
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result.get()).usingRecursiveComparison()
                .usingOverriddenEquals().isEqualTo(user);

    }

    /**
     * Tests ability to add multiple and return multiple users within the database
     */
    @Test
    @Transactional
    public void UserRepository_TestMultipleUsersSaveAndReturn_ReturnSavedUsers(){
        User user = TestDataUtil.createTestUserOne();
        User user2 = TestDataUtil.createTestUserTwo();
        User user3 = TestDataUtil.createTestUserThree();
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
        Iterable<User> result = userRepository.findAll();
        assertThat(result).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(user, user2, user3);
    }

    /**
     * Makes sure that an existing user can have its information changed and updated
     * with the changes being reflected in the database
     */
    @Test
    @Transactional
    public void UserRepository_UpdateUser_ReturnUpdatedUser(){
        User user = TestDataUtil.createTestUserOne();
        userRepository.save(user);
        user.setUsername("JoeJack123");
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .usingOverriddenEquals().isEqualTo(user);
    }

    /**
     * Makes sure that a user can be deleted and is no longer
     * within the database
     */
    @Test
    @Transactional
    public void UserRepository_DeleteUser_ReturnDeletedUser(){
        User user = TestDataUtil.createTestUserOne();
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void UserRepository_TestAddingTasksToUser_ReturnAddedTasks(){
        User user = TestDataUtil.createTestUserOne();
        Task task = TestDataUtil.createTestTaskOne();
        task.setId(1L);
        Task task2 = TestDataUtil.createTestTaskTwo();
        task2.setId(2L);
        Task task3 = TestDataUtil.createTestTaskThree();
        task3.setId(3L);
        user.getTasks().add(task);
        user.getTasks().add(task2);
        user.getTasks().add(task3);
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result.get().getTasks()).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(task, task2, task3);

    }
}
