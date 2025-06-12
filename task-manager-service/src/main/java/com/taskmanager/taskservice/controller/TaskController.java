package com.taskmanager.taskservice.controller;

import java.util.List;

import com.taskmanager.taskservice.dto.TaskDto;
import com.taskmanager.taskservice.dto.TaskRequestDto;
import com.taskmanager.taskservice.service.TaskService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/cached")
    public ResponseEntity<List<TaskDto>> getAllTasksWithCaching() {
        return ResponseEntity.ok(taskService.getAllTasksWithCaching());
    }

    @GetMapping("/paged-cached")
    public ResponseEntity<Page<TaskDto>> getPagedTasksCached(Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasksPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskRequestDto request) {
        TaskDto created = taskService.createTask(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto request) {
        TaskDto updated = taskService.updateTask(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
