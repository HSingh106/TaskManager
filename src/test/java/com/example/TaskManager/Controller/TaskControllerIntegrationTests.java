package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.TaskDTO;
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

    private DateTimeFormatter formatter;

    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, TaskService taskService, UserService userService) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
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

    @Test
    public void testGetTaskEndpoint() throws Exception{
        User user = TestDataUtil.createTestUserOne();
        Task task = TestDataUtil.createTestTaskOne();
        User savedUser = userService.save(user);
        Task savedTask = taskService.save(savedUser.getId(), task);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/task/{taskId}", savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
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

    @Test
    public void testGetListAllUserTaskEndpoint() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        Task taskTest = TestDataUtil.createTestTaskOne();
        User savedUser = userService.save(testUser);
        Task taskTestTwo = TestDataUtil.createTestTaskTwo();
        Task taskTestThree = TestDataUtil.createTestTaskThree();
        taskService.save(savedUser.getId(), taskTest);
        taskService.save(savedUser.getId(), taskTestTwo);
        taskService.save(savedUser.getId(), taskTestThree);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/{id}/tasks", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].taskName").value("Wake Up")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].taskDescription").value("What time I plan on waking up")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskType").value("Simple")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskStartDate").value(LocalDateTime.of(2024,8,1,8,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskEndDate").value(LocalDateTime.of(2024,8,1,8,15,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].taskName").value("Pray")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].taskDescription").value("Time for prayer")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskType").value("Religious")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskStartDate").value(LocalDateTime.of(2024,8,1,12,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskEndDate").value(LocalDateTime.of(2024,8,1,12,30).format(formatter))
        );
    }

    @Test
    public void testGetAllUserTaskWithNameEndpoint() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        Task taskTest = TestDataUtil.createTestTaskOne();
        Task taskTestTwo = TestDataUtil.createTestTaskOne();
        taskTestTwo.setTaskStatus("Complete");
        Task taskTestThree = TestDataUtil.createTestTaskOne();
        taskTestThree.setTaskDescription("Chest & Triceps");
        User savedUser = userService.save(testUser);
        taskService.save(savedUser.getId(), taskTest);
        taskService.save(savedUser.getId(), taskTestTwo);
        taskService.save(savedUser.getId(), taskTestThree);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/{id}/tasks/{name}", savedUser.getId(), "Gym")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskStatus").value("Complete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[1].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].taskDescription").value("Chest & Triceps")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$[2].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        );

    }

    @Test
    public void testDeleteTaskEndpointReturns404() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/delete/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteUserEndpoint() throws Exception{
        User user = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(user);
        Task task = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), task);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/delete/{id}", savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testPartialUpdateTaskEndpoint() throws Exception{
        User user = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(user);

        Task task = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), task);
        TaskDTO taskDTO = TestDataUtil.createTestTaskDTOOne();
        String taskDTOJson = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/task/PartialUpdate/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDTOJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedTask.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskName").value("TaskDTO")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskDescription").value("DescriptionDTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        );
    }

    @Test
    public void testFullUpdateReturns404() throws Exception{
        TaskDTO testTaskDTO = TestDataUtil.createTestTaskDTOOne();
        String taskJson = objectMapper.writeValueAsString(testTaskDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/task/complete/704")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFullUpdateReturns200() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(testUser);
        Task testTask = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), testTask);

        TaskDTO testTaskDTO = TestDataUtil.createTestTaskDTOTwo();
        String authorDTOJson = objectMapper.writeValueAsString(testTaskDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/task/complete/{taskId}", savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDTOJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedTask.getId())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskName").value("TaskDTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskDescription").value("DescriptionDTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStatus").value("DTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskType").value("DTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskStartDate").value(LocalDateTime.of(2024,8,1,8,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.taskEndDate").value(LocalDateTime.of(2024,8,1,8,15,0).format(formatter)
        ));
    }

}
