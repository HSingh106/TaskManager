package com.example.TaskManager.Model.DTO;

import com.example.TaskManager.Model.Entities.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO{

    private Long id;

    private String username;

    private String password;
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

}
