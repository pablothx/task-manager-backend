package com.taskmanager.taskservice.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TaskDto {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDate duedate;
    private final String status;
    private final String priority;
    private final Long assignedToId;

    public TaskDto(Long id, String title, String description, LocalDate duedate, String status, String priority, Long assignedToId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duedate = duedate;
        this.status = status;
        this.priority = priority;
        this.assignedToId = assignedToId;
    }
}
