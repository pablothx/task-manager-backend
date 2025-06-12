package com.taskmanager.taskservice.service;

import java.util.List;

import com.taskmanager.taskservice.dto.TaskDto;
import com.taskmanager.taskservice.dto.TaskRequestDto;
import com.taskmanager.taskservice.entity.Task;
import com.taskmanager.taskservice.repository.TaskRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Task not found"));

        return mapToDto(task);
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                             .map(this::mapToDto)
                             .toList();
    }

    @Cacheable("allTasksWithCaching")
    public List<TaskDto> getAllTasksWithCaching() {
        return taskRepository.findAll().stream()
                             .map(this::mapToDto)
                             .toList();
    }

    @Cacheable(value = "pagedTasks", key = "'page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize + '_sort_' + #pageable.sort.toString()")
    public Page<TaskDto> getAllTasksPaged(Pageable pageable) {
        return taskRepository.findAll(pageable)
                             .map(this::mapToDto);
    }

    public TaskDto createTask(TaskRequestDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        if (dto.getAssignedToId() != null) {
            task.setAssignedToId(dto.getAssignedToId());
        }

        return mapToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(Long id, TaskRequestDto dto) {
        Task existingTask = taskRepository.findById(id)
                                          .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(dto.getTitle());
        existingTask.setDescription(dto.getDescription());
        existingTask.setDueDate(dto.getDueDate());
        existingTask.setStatus(dto.getStatus());
        existingTask.setPriority(dto.getPriority());
        existingTask.setAssignedToId(dto.getAssignedToId());

        return mapToDto(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskDto mapToDto(Task task) {
        return new TaskDto(
              task.getId(),
              task.getTitle(),
              task.getDescription(),
              task.getDueDate(),
              task.getStatus(),
              task.getPriority(),
              task.getAssignedToId()
        );
    }
}
