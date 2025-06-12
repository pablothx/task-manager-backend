package com.taskmanager.takservice.repository;

import com.taskmanager.takservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

