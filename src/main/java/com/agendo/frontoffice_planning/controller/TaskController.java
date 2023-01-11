package com.agendo.frontoffice_planning.controller;

import com.agendo.frontoffice_planning.controller.exception.action.ActionNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.share.ShareNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.task.TaskNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.planning.PlanningNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.share.ShareReadOnlyException;
import com.agendo.frontoffice_planning.controller.exception.user.UserNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.user.UserNotOwnerException;
import com.agendo.frontoffice_planning.controller.models.TaskDTO;
import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Users;
import com.agendo.frontoffice_planning.service.PlanningService;
import com.agendo.frontoffice_planning.service.ShareService;
import com.agendo.frontoffice_planning.service.TaskService;
import com.agendo.frontoffice_planning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private PlanningService planningService;

    @Autowired
    private UserService userService;


    /**
     * Find a task based on ID
     *
     * @param idTask Task id user is looking for
     * @param users  Getting authenticated user from the auth filter
     * @return taskDTO Formatted Task object for Front-End
     */

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@RequestAttribute("user") Users users, @PathVariable("id") long idTask) {
        try {
            TaskDTO taskDTO = taskService.getTaskDTOById(idTask, users);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * @param users    Getting authenticated user from the auth filter
     * @param idTask   Task id user is looking for
     * @param idUser   id user requesting the GET 
     * @return taskDTO with ID if success, otherwise returns an error
     */
    @GetMapping("/task/shared/{id}")
    public ResponseEntity<TaskDTO> getTaskByIdShared(@RequestAttribute("user") Users users, @PathVariable("id") long idTask, @RequestParam("idUser") long idUser, @RequestParam("idPlanning") long idPlanning) {
        try {
            TaskDTO taskDTO = taskService.getTaskDTOByIdShared(idTask, idPlanning, idUser, users);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
        } catch (TaskNotFoundException | PlanningNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ShareNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Always need to check if it's owner or shared

    /**
     * Create a Task from DTO
     *
     * @param taskDTO Task to create
     * @param users   Getting authenticated user from the auth filter
     * @return taskDTO with ID if success, otherwise returns an error
     */
    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestAttribute("user") Users users, @RequestBody TaskDTO taskDTO) {
        try {
            Planning planning = planningService.getPlanningById(taskDTO.getIdPlanning());
            userService.isOwner(planning, users);
            TaskDTO createdTaskDTO = taskService.createTask(taskDTO, planning, users);
            return ResponseEntity.status(HttpStatus.OK).body(createdTaskDTO);
        } catch (PlanningNotFoundException | UserNotFoundException | ActionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Create a Task from DTO and from a shared user
     *
     * @param taskDTO Task to create
     * @param users   Getting authenticated user from the auth filter
     * @return taskDTO with ID if success, otherwise returns an error
     */
    @PostMapping("/task/shared")
    public ResponseEntity<TaskDTO> createTaskShared(@RequestAttribute("user") Users users, @RequestBody TaskDTO taskDTO) {

        try {
            Planning planning = planningService.getPlanningById(taskDTO.getIdPlanning());
            shareService.shareExists(planning, users);
            shareService.isFullAccess(planning, users);
            TaskDTO createdTaskDTO = taskService.createTask(taskDTO, planning, users);
            return ResponseEntity.status(HttpStatus.OK).body(createdTaskDTO);
        } catch (PlanningNotFoundException | UserNotFoundException | ActionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ShareNotFoundException | ShareReadOnlyException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // If we definitly delete, action "Delete" in table is useless
    // Maybe not delete, but set as delete but table will grow . Just keeping task as delete for logging.

    /**
     * Edit a Task with a DTO (dedicated for the owner)
     *
     * @param taskDTO Task to update (data which will update current same task id)
     * @param users   Getting authenticated user from the auth filter
     * @return taskDTO updated (name, dates, description, new event Update)
     */
    @PutMapping("/task/edit")
    public ResponseEntity<TaskDTO> editTask(@RequestAttribute("user") Users users, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Planning planning = planningService.getPlanningById(taskDTO.getIdPlanning());
            userService.isOwner(planning, users);
            taskDTO = taskService.updateTask(taskDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
        } catch (PlanningNotFoundException | UserNotFoundException | TaskNotFoundException |
                 ActionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Edit a Task with a DTO (dedicated for sharing)
     *
     * @param users   Getting authenticated user from the auth filter
     * @param taskDTO Task to update (data which will update current same task id)
     * @return taskDTO updated (name, dates, description, new event Update)
     */
    @PutMapping("/task/shared/edit")
    public ResponseEntity<TaskDTO> editTaskShared(@RequestAttribute("user") Users users, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Planning planning = planningService.getPlanningById(taskDTO.getIdPlanning());
            shareService.shareExists(planning, users);
            shareService.isFullAccess(planning, users);
            taskDTO = taskService.updateTask(taskDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
        } catch (PlanningNotFoundException | UserNotFoundException | TaskNotFoundException |
                 ActionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ShareNotFoundException | ShareReadOnlyException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Delete a Task based on ID
     *
     * @param id task id to delete
     * @return HttpStatus success or not found
     */
    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@RequestAttribute("user") Users users, @PathVariable("id") long id) {

        try {
            taskService.delete(id, users);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Delete a Shared Task based on ID
     *
     * @param id       task id to delete
     * @param idUser   id user requesting the remove
     * @param users    Getting authenticated user from the auth filter
     * @return HttpStatus success or not found
     */
    @DeleteMapping("/task/shared/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTaskShared(@RequestAttribute("user") Users users, @PathVariable("id") long id, @RequestParam("idUser") long idUser, @RequestParam("idPlanning") long idPlanning) {

        try {
            taskService.deleteShared(id, idPlanning, idUser, users);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException | TaskNotFoundException | PlanningNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ShareNotFoundException | ShareReadOnlyException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
