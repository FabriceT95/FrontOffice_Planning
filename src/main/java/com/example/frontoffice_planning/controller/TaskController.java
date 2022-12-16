package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.entity.Event;
import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.repository.ActionRepository;
import com.example.frontoffice_planning.repository.TaskRepository;
import com.example.frontoffice_planning.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ActionRepository actionRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository, ActionRepository actionRepository, TaskService taskService){
        this.taskRepository = taskRepository;
        this.actionRepository = actionRepository;
        this.taskService = taskService;
    }

    @GetMapping("/task/id/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long idTask){

        Optional<Task> seachedTask = taskRepository.findById(idTask);

        if (seachedTask.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(seachedTask.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody Task task){

        Task newTask = new Task();

        newTask.setNameTask(task.getNameTask());
        newTask.setDescription(task.getDescription());
        newTask.setDateTaskStart(task.getDateTaskStart());
        newTask.setDateTaskEnd(task.getDateTaskEnd());
        newTask.setDateCreated(task.getDateCreated());
        newTask.addEvent(new Event(LocalDateTime.now(), actionRepository.findById(1L).get()));

        taskService.createTask(newTask);

        return  ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") long id){

        Optional<Task> deleteTask = taskRepository.findById(id);

        if (deleteTask.isPresent()){
            taskService.delete(deleteTask.get());
            return  ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/task/edit/{id}")
    public ResponseEntity<Task> editTask(@RequestBody Task task, @PathVariable("id") long id){

        Optional<Task> updatedTask = taskRepository.findById(id);

        if (updatedTask.isPresent()){

            updatedTask.get().setNameTask(task.getNameTask());
            updatedTask.get().setDateTaskStart(task.getDateTaskStart());
            updatedTask.get().setDateCreated(task.getDateCreated());
            updatedTask.get().setDateTaskEnd(task.getDateTaskEnd());
            updatedTask.get().setDescription(task.getDescription());
            updatedTask.get().setPlanning(task.getPlanningByIdPlanning());
            updatedTask.get().setEvents(task.getEventsByIdTask());

            taskService.createTask(updatedTask.get());
            return  ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}