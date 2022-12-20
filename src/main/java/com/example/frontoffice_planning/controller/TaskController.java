package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.models.ActionDTO;
import com.example.frontoffice_planning.controller.models.EventDTO;
import com.example.frontoffice_planning.controller.models.TaskDTO;
import com.example.frontoffice_planning.entity.Action;
import com.example.frontoffice_planning.entity.Event;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.repository.ActionRepository;
import com.example.frontoffice_planning.repository.EventRepository;
import com.example.frontoffice_planning.repository.TaskRepository;
import com.example.frontoffice_planning.service.PlanningService;
import com.example.frontoffice_planning.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ActionRepository actionRepository;

    private final EventRepository eventRepository;
    private final TaskService taskService;

    private final PlanningService planningService;

    public TaskController(TaskRepository taskRepository, ActionRepository actionRepository, TaskService taskService, PlanningService planningService, EventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.actionRepository = actionRepository;
        this.taskService = taskService;
        this.planningService = planningService;
        this.eventRepository = eventRepository;
    }

    /**
     * Find a task based on ID
     *
     * @param idTask
     * @return taskDTO Formatted Task object for Front-End
     */

    @GetMapping("/task/id/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") long idTask) {

        Optional<Task> seachedTask = taskRepository.findById(idTask);

        if (seachedTask.isPresent()) {
            Task task = seachedTask.get();

            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setIdTask(task.getIdTask());
            taskDTO.setNameTask(task.getNameTask());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setDateCreated(task.getDateCreated());
            taskDTO.setDateTaskStart(task.getDateTaskStart());
            taskDTO.setDateTaskEnd(task.getDateTaskEnd());
            taskDTO.setEventList(task.getEventsByIdTask().stream().map(event -> {
                EventDTO eventDTO = new EventDTO();
                eventDTO.setIdPlanning(event.getPlanning().getIdPlanning());
                eventDTO.setDateCreated(event.getDateCreated());
                ActionDTO actionDTO = new ActionDTO();
                actionDTO.setIdAction(event.getAction().getIdAction());
                actionDTO.setName(event.getAction().getName());
                eventDTO.setActionDTO(actionDTO);
                eventDTO.setIdEvent(event.getIdEvent());
                return eventDTO;
            }).collect(Collectors.toList()));

            return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    // Always need to check if it's owner or shared

    /**
     * Create a Task from DTO
     * @param taskDTO
     * @return taskDTO with ID
     */
    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {

        Task newTask = new Task();

        newTask.setNameTask(taskDTO.getNameTask());
        newTask.setDescription(taskDTO.getDescription());
        newTask.setDateTaskStart(taskDTO.getDateTaskStart());
        newTask.setDateTaskEnd(taskDTO.getDateTaskEnd());
        newTask.setDateCreated(LocalDateTime.now());
        newTask.setPlanning(planningService.getPlanningById(taskDTO.getIdPlanning()).get());
        newTask.addEvent(new Event(LocalDateTime.now(), actionRepository.findById(1L).get()));

        Task createdTask = taskService.createTask(newTask);

        taskDTO.setDateCreated(createdTask.getDateCreated());
        taskDTO.setIdTask(createdTask.getIdTask());

        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
    }


    // If we definitly delete, action "Delete" in table is useless
    // Maybe not delete, but set as delete but table will grow . Just keeping task as delete for logging.

    /**
     * Delete a Task based on ID
     * @param id
     * @return HttpStatus success or not found
     */
    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {

        Optional<Task> deleteTask = taskRepository.findById(id);

        if (deleteTask.isPresent()) {
            taskService.delete(deleteTask.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Edit a Task with a DTO
     * @param taskDTO
     * @param id Task Id
     * @return taskDTO updated (name, dates, description, new event Update)
     */
    @PutMapping("/task/edit/{id}")
    public ResponseEntity<TaskDTO> editTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable("id") long id) {

        Optional<Task> optUpdatedTask = taskRepository.findById(id);
        if (optUpdatedTask.isPresent()) {
            Optional<Planning> optPlanning = planningService.getPlanningById(taskDTO.getIdPlanning());
            if (optPlanning.isPresent()) {
                Task updatedTask = optUpdatedTask.get();
                updatedTask.setNameTask(taskDTO.getNameTask());
                updatedTask.setDateTaskStart(taskDTO.getDateTaskStart());
                updatedTask.setDateTaskEnd(taskDTO.getDateTaskEnd());
                updatedTask.setDescription(taskDTO.getDescription());
                Event event = new Event();
                event.setTask(updatedTask);
                event.setDateCreated(LocalDateTime.now());
                event.setPlanning(optPlanning.get());
                // Need to get the user
                //event.setUser();
                event.setAction(actionRepository.findById(2L).get());

                Event savedEvent = eventRepository.save(event);

                updatedTask.addEvent(savedEvent);

                Task task = taskService.createTask(updatedTask);

                // Sets up a new EventDTO which contains an ActionDTO
                EventDTO eventDTO = new EventDTO();
                ActionDTO actionDTO = new ActionDTO();
                actionDTO.setIdAction(savedEvent.getAction().getIdAction());
                actionDTO.setName(savedEvent.getAction().getName());

                // Add actionDTO to the eventDTO
                eventDTO.setActionDTO(actionDTO);
                eventDTO.setIdEvent(savedEvent.getIdEvent());
                eventDTO.setDateCreated(savedEvent.getDateCreated());
                eventDTO.setIdPlanning(savedEvent.getPlanning().getIdPlanning());


                taskDTO.setNameTask(task.getNameTask());
                taskDTO.setDateTaskStart(task.getDateTaskStart());
                taskDTO.setDateTaskEnd(task.getDateTaskEnd());
                taskDTO.setDescription(task.getDescription());
                taskDTO.addEvent(eventDTO);

                return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
