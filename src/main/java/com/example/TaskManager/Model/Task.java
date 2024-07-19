package com.example.TaskManager.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * Layout information contained within task entities
 * and modify database schema to include task table
 * Also generate id values and use foreign key to connect
 * tasks to the users they belong to (establishing many-to-one relation)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    private Long id;
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private String taskType;
    private LocalDateTime taskStartDate;
    private LocalDateTime taskEndDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
