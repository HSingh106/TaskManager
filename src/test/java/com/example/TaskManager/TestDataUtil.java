package com.example.TaskManager;

import com.example.TaskManager.Model.User;

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


}
