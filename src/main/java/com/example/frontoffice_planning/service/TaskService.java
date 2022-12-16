package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository TaskRepository) {
        this.taskRepository = TaskRepository;
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Task task){taskRepository.delete(task);}
}
