package com.example.TaskManager.Model.DTO;

import com.example.TaskManager.Model.Entities.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the DTO class corresponding to the user entity
 * Meant to help restrict controller access to repository layer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO{

    private String username;

    private String password;

}
