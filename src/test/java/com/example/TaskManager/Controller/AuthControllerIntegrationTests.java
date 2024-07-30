package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.Entities.Role;
import com.example.TaskManager.Service.RoleService;
import com.example.TaskManager.Service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthControllerIntegrationTests {


    private UserService userService;

    private MockMvc mockMvc;

    private RoleService roleService;

    @Autowired
    public AuthControllerIntegrationTests(MockMvc mockMvc, UserService userService, RoleService roleService) throws Exception {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.roleService = roleService;
        roleService.save(new Role(1L, "ADMIN"));
        roleService.save(new Role(2L, "USER"));
       // userService.save(UserEntity.builder().username("testuser").password("testpassword").build());
    }

    @Test
    public void testRegisterReturns200() throws Exception {
        String registerRequest =  "{ \"username\": \"testuser\", \"password\": \"testpassword\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLoginReturns200() throws Exception {
        String loginRequest = "{ \"username\": \"testuser\", \"password\": \"testpassword\" }";
        String registerRequest =  "{ \"username\": \"testuser\", \"password\": \"testpassword\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("accessToken").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("tokenType").value("Bearer "));
    }


}
