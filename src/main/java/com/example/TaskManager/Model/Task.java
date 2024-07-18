package com.example.TaskManager.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    private Long id;
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private String taskType;
    private Date taskStartDate;
    private Date taskEndDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
