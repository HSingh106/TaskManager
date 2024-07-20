package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Task;
import com.example.TaskManager.Model.User;
import com.example.TaskManager.TestDataUtil;
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
        taskRepository.save(task);
        Optional<Task> taskOptional = taskRepository.findById(task.getId());
        assertThat(taskOptional).isPresent();
        assertThat(taskOptional.get()).isEqualTo(task);
    }

    @Test
    public void TaskRepository_CreateAndRecallMultipleTasks_ReturnCreatedTasks(){
        User user = TestDataUtil.createTestUserOne();
        userRepository.save(user);
        Task task1 = TestDataUtil.createTestTaskOne();
        taskRepository.save(task1);
        Task task2 = TestDataUtil.createTestTaskTwo();
        taskRepository.save(task2);
        Task task3 = TestDataUtil.createTestTaskThree();
        taskRepository.save(task3);
        Iterable<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(3).contains(task1,task2, task3);
    }

}
