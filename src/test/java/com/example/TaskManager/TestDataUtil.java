package com.example.TaskManager;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.User;

import java.time.LocalDateTime;

public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static User createTestUserOne(){
        return User.builder()
                .id(1L)
                .username("JohnDoe241")
                .password("JaneDoe153")
                .build();
    }

    public static User createTestUserTwo(){
        return User.builder()
                .id(2L)
                .username("RoniV43")
                .password("123")
                .build();
    }
    public static User createTestUserThree(){
        return User.builder()
                .id(3L)
                .username("AllIn43")
                .password("AllOnBlack342")
                .build();
    }

    public static UserDTO createTestUserDTOOne(){
        return UserDTO.builder()
                .id(1L)
                .username("JoeDTO")
                .password("JaneDTO")
                .build();
    }

    public static Task createTestTaskOne(){
        return Task.builder()
                .taskName("Gym")
                .taskDescription("Complete Workout")
                .taskType("Health")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,10,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,12,0))
                .build();

    }
    public static Task createTestTaskTwo(){
        return Task.builder()
                .taskName("Wake Up")
                .taskDescription("What time I plan on waking up")
                .taskType("Simple")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,8,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,8,15))
                .build();
    }
    public static Task createTestTaskThree(){
        return Task.builder()
                .taskName("Pray")
                .taskDescription("Time for prayer")
                .taskType("Religious")
                .taskStatus("Incomplete")
                .taskStartDate(LocalDateTime.of(2024,8,1,12,0))
                .taskEndDate(LocalDateTime.of(2024,8,1,12,30))
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
