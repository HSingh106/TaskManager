package com.example.TaskManager;

import com.example.TaskManager.Model.Task;
import com.example.TaskManager.Model.User;

import java.time.LocalDateTime;

public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static User createTestUserOne(){
        return User.builder()
                .username("JohnDoe241")
                .password("JaneDoe153")
                .build();
    }

    public static User createTestUserTwo(){
        return User.builder()
                .username("RoniV43")
                .password("123")
                .build();
    }
    public static User createTestUserThree(){
        return User.builder()
                .username("AllIn43")
                .password("AllOnBlack342")
                .build();
    }

    public static Task createTestTaskOne(final User user){
        return Task.builder()
                .taskName("Gym")
                .taskDescription("Complete Workout")
                .taskType("Health")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,10,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,12,0))
                .user(user)
                .build();

    }
    public static Task createTestTaskTwo(final User user){
        return Task.builder()
                .taskName("Wake Up")
                .taskDescription("What time I plan on waking up")
                .taskType("Simple")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,8,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,8,15))
                .user(user)
                .build();
    }
    public static Task createTestTaskThree(final User user){
        return Task.builder()
                .taskName("Pray")
                .taskDescription("Time for prayer")
                .taskType("Religious")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,12,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,12,30))
                .user(user)
                .build();
    }

    /**
     * private Long id;
     *     private String taskName;
     *     private String taskDescription;
     *     private String taskStatus;
     *     private String taskType;
     *     private Date taskStartDate;
     *     private Date taskEndDate;
     *     @ManyToOne(cascade = CascadeType.ALL)
     *     @JoinColumn(name = "user_id")
     *     private User user;
     */


}
