package com.taskmanager.takservice.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String priority;
    private Long assignedToId;
}
