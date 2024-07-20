package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Task;
import com.example.TaskManager.Model.User;
import com.example.TaskManager.TestDataUtil;
import jakarta.transaction.Transactional;
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
public class TaskRepositoryIntegrationTests {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskRepositoryIntegrationTests(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Tests to make sure a task can be created, saved, and recalled within a database
     */
    @Test
    public void TaskRepository_TaskCreateAndRecall_ReturnCreatedTask(){
        User user = TestDataUtil.createTestUserTwo();
        userRepository.save(user);
        Task task = TestDataUtil.createTestTaskOne();
        task.setId(1L);
        user.getTasks().add(task);
        userRepository.save(user);
        Iterable<Task> result = taskRepository.findAll();
        assertThat(result).hasSize(1).containsExactly(task);
    }

    @Test
    @Transactional
    public void TaskRepository_CreateAndRecallMultipleTasks_ReturnCreatedTasks(){
        User user = TestDataUtil.createTestUserOne();
        userRepository.save(user);
        Task task1 = TestDataUtil.createTestTaskOne();
        Task task2 = TestDataUtil.createTestTaskTwo();
        Task task3 = TestDataUtil.createTestTaskThree();
        task1.setId(1L);
        task2.setId(2L);
        task3.setId(3L);
        user.getTasks().add(task1);
        user.getTasks().add(task2);
        user.getTasks().add(task3);
        userRepository.save(user);
        Iterable<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(3).contains(task1,task2, task3);
    }

}
