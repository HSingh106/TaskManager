package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.TaskDTO;
import com.example.TaskManager.Model.Entities.Role;
import com.example.TaskManager.Model.Entities.Task;
import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.Service.RoleService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private String token;

    private RoleService roleService;

    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, TaskService taskService, UserService userService, RoleService roleService) throws Exception {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.taskService = taskService;
        this.objectMapper = new ObjectMapper();
        this.roleService = roleService;
        roleService.save(new Role(1L, "ADMIN"));
        roleService.save(new Role(2L, "USER"));
        objectMapper.registerModule(new JavaTimeModule());
        register();
        token = getAccessToken();
        userService.save(UserEntity.builder().username("testuser").password("testpassword").build());
    }

    private void register() throws Exception {
        String registerRequest =  "{ \"username\": \"testuser\", \"password\": \"testpassword\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest));
    }

    private String getAccessToken() throws Exception {
        String loginRequest = "{ \"username\": \"testuser\", \"password\": \"testpassword\" }";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseString).get("accessToken").asText();
    }

    @Test
    public void testCreateTaskEndpoint() throws Exception{
        Task taskTest = TestDataUtil.createTestTaskOne();
        UserEntity userTask = TestDataUtil.createTestUserOne();

        UserEntity savedUser = userService.save(userTask);
        String taskJson = objectMapper.writeValueAsString(taskTest);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/{id}", savedUser.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                status().isCreated()
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
        UserEntity user = TestDataUtil.createTestUserOne();
        Task task = TestDataUtil.createTestTaskOne();
        UserEntity savedUser = userService.save(user);
        Task savedTask = taskService.save(savedUser.getId(), task);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/{taskId}", savedTask.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
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
        UserEntity testUser = TestDataUtil.createTestUserOne();
        Task taskTest = TestDataUtil.createTestTaskOne();
        UserEntity savedUser = userService.save(testUser);
        Task taskTestTwo = TestDataUtil.createTestTaskTwo();
        Task taskTestThree = TestDataUtil.createTestTaskThree();
        taskService.save(savedUser.getId(), taskTest);
        taskService.save(savedUser.getId(), taskTestTwo);
        taskService.save(savedUser.getId(), taskTestThree);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/users/{id}", savedUser.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "3")
        ).andExpect(status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].taskName").value("Wake Up")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].taskDescription").value("What time I plan on waking up")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskType").value("Simple")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskStartDate").value(LocalDateTime.of(2024,8,1,8,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskEndDate").value(LocalDateTime.of(2024,8,1,8,15,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].taskName").value("Pray")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].taskDescription").value("Time for prayer")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskType").value("Religious")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskStartDate").value(LocalDateTime.of(2024,8,1,12,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskEndDate").value(LocalDateTime.of(2024,8,1,12,30).format(formatter))
        );
    }

    @Test
    public void testGetAllUserTaskWithNameEndpoint() throws Exception{
        UserEntity testUser = TestDataUtil.createTestUserOne();
        Task taskTest = TestDataUtil.createTestTaskOne();
        Task taskTestTwo = TestDataUtil.createTestTaskOne();
        taskTestTwo.setTaskStatus("Complete");
        Task taskTestThree = TestDataUtil.createTestTaskOne();
        taskTestThree.setTaskDescription("Chest & Triceps");
        UserEntity savedUser = userService.save(testUser);
        taskService.save(savedUser.getId(), taskTest);
        taskService.save(savedUser.getId(), taskTestTwo);
        taskService.save(savedUser.getId(), taskTestThree);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/users/{id}/{name}", savedUser.getId(), "Gym")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "3")
        ).andExpect(status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[1].taskDescription").value("Complete Workout")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskStatus").value("Complete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[1].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].taskName").value("Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[2].taskDescription").value("Chest & Triceps")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskStatus").value("Incomplete")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskType").value("Health")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskStartDate").value(LocalDateTime.of(2024,8,1,10,0,0).format(formatter))
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[2].taskEndDate").value(LocalDateTime.of(2024,8,1,12,0,0).format(formatter))
        );

    }


    @Test
    public void testDeleteUserEndpoint() throws Exception{
        UserEntity user = TestDataUtil.createTestUserOne();
        UserEntity savedUser = userService.save(user);
        Task task = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), task);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/delete/{id}", savedTask.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testPartialUpdateTaskEndpoint() throws Exception{
        UserEntity user = TestDataUtil.createTestUserOne();
        UserEntity savedUser = userService.save(user);

        Task task = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), task);
        TaskDTO taskDTO = TestDataUtil.createTestTaskDTOOne();
        String taskDTOJson = objectMapper.writeValueAsString(taskDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/tasks/PartialUpdate/" + savedUser.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDTOJson)
        ).andExpect(status().isOk()
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
    public void testFullUpdateReturns200() throws Exception{
        UserEntity testUser = TestDataUtil.createTestUserOne();
        UserEntity savedUser = userService.save(testUser);
        Task testTask = TestDataUtil.createTestTaskOne();
        Task savedTask = taskService.save(savedUser.getId(), testTask);

        TaskDTO testTaskDTO = TestDataUtil.createTestTaskDTOTwo();
        String authorDTOJson = objectMapper.writeValueAsString(testTaskDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/complete/{taskId}", savedTask.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDTOJson)
        ).andExpect(status().isOk()
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
