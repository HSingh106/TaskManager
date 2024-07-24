package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Service.TaskService;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {

    private TaskService taskService;

    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, TaskService taskService, UserService userService) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.taskService = taskService;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateTaskEndpoint() throws Exception{
        Task taskTest = TestDataUtil.createTestTaskOne();
        User userTask = TestDataUtil.createTestUserOne();

        User savedUser = userService.save(userTask);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String taskJson = objectMapper.writeValueAsString(taskTest);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter)));
    }

}
