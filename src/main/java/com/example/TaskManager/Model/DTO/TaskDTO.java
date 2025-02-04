package com.example.TaskManager.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents the DTO class corresponding to the task entity
 * Meant to help restrict controller access to repository layer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private String taskType;
    private LocalDateTime taskStartDate;
    private LocalDateTime taskEndDate;
}
