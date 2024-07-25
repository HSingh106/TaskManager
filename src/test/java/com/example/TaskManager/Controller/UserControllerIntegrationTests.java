package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.User;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateUserEndpoint() throws Exception{
        User userTest = TestDataUtil.createTestUserOne();
        String userJson = objectMapper.writeValueAsString(userTest);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("JohnDoe241")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("JaneDoe153")
        );
    }
    @Test
    public void testGetListUserEndpoint() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        userService.save(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "1")
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].username").value("JohnDoe241")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].password").value("JaneDoe153")
        );
    }

    @Test
    public void testGetSingleUserEndpoint() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        userService.save(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/{id}", testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("JohnDoe241")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("JaneDoe153")
        );
    }

    @Test
    public void testGetUserReturns404() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFullUpdateReturns404() throws Exception{
        UserDTO testUserDTO = TestDataUtil.createTestUserDTOOne();
        String userJson = objectMapper.writeValueAsString(testUserDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/Update/300")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFullUpdateReturns200() throws Exception{
        User testUser = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(testUser);

        UserDTO testUserDTO = TestDataUtil.createTestUserDTOOne();
        String authorDTOJson = objectMapper.writeValueAsString(testUserDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/user/Complete/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDTOJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.username").value("JoeDTO")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.password").value("JaneDTO"));

    }

    @Test
    public void testPartialUpdateReturns200() throws Exception{
        User user = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(user);

        UserDTO testUserDTO = TestDataUtil.createTestUserDTOOne();
        String userDTOJson = objectMapper.writeValueAsString(testUserDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/user/Update/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTOJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedUser.getId())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.username").value(testUserDTO.getUsername())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.password").value(testUserDTO.getPassword()));
    }

    @Test
    public void testDeleteAuthorReturns204ForNonExistingUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/delete/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteAuthorReturns204ForExistingUser() throws Exception{
        User user = TestDataUtil.createTestUserOne();
        User savedUser = userService.save(user);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/delete/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }



}
