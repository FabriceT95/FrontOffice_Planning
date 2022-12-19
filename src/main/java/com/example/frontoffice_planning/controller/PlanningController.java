package com.example.frontoffice_planning.controller;


import com.example.frontoffice_planning.controller.models.PlanningDTO;
import com.example.frontoffice_planning.controller.models.ShareDTO;
import com.example.frontoffice_planning.controller.models.TaskDTO;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.service.PlanningService;
import com.example.frontoffice_planning.service.ShareService;
import com.example.frontoffice_planning.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class PlanningController {

    private final UserService userService;
    private final PlanningService planningService;

    private final ShareService shareService;

    public PlanningController(UserService userService, PlanningService planningService, ShareService shareService) {
        this.userService = userService;
        this.planningService = planningService;
        this.shareService = shareService;
    }

    // Maybe allowing the owner to get by this path aswell ?
    // For now, only shared people can go this way, not the owner
    // Owner only gets it from "UserControlle" API
    @GetMapping("/planning/auth")
    public ResponseEntity<PlanningDTO> getPlanningByShare(@RequestBody ShareDTO shareDTO) {
        Optional<Users> optUser = userService.getUserById(shareDTO.getUserId());
        Optional<Planning> optPlanning = planningService.getPlanningById(shareDTO.getPlanningId());
        // Need security : Verify if user has access with his id to this planning
        if (optUser.isPresent() && optPlanning.isPresent()) {
            Optional<Share> optShare = shareService.getShareByPlanningAndUser(optPlanning.get(), optUser.get());
            if (optShare.isPresent()) {
                Planning planning = optPlanning.get();
                PlanningDTO planningDTO = new PlanningDTO();
                planningDTO.setIdPlanning(planning.getIdPlanning());
                planningDTO.setNamePlanning(planning.getNamePlanning());
                planningDTO.setDateCreated(planning.getDateCreated());
                planningDTO.setIdOwner(planning.getUser().getIdUser());
                planningDTO.setReadOnly(optShare.get().getIsReadOnly());

                List<Task> taskList = planning.getTasks();
                List<TaskDTO> taskDTOList = taskList.stream().map(task -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setIdTask(task.getIdTask());
                    taskDTO.setNameTask(task.getNameTask());
                    taskDTO.setDescription(task.getDescription());
                    taskDTO.setDateCreated(task.getDateCreated());
                    taskDTO.setDateTaskStart(task.getDateTaskStart());
                    taskDTO.setDateTaskEnd(task.getDateTaskEnd());
                    // We don't need to set event list. We don't use it in the planning overview.
                    // taskDTO.setEventList();
                    return taskDTO;
                }).toList();

                planningDTO.setTaskList(taskDTOList);
                return ResponseEntity.status(HttpStatus.OK).body(planningDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update planning fields (mostly name)
    // We can do 2 request : One specified for the owner, and other one for any shared people
    // Or just one mixing both
    // PARAMS IN PROGRESS
    @PutMapping("/planning/auth?name={name}")
    public ResponseEntity<PlanningDTO> updatePlanningName(@PathVariable("name") String name, @RequestBody ShareDTO shareDTO) {
        Optional<Users> optUser = userService.getUserById(shareDTO.getUserId());
        Optional<Planning> optPlanning = planningService.getPlanningById(shareDTO.getPlanningId());
        // Need security : Verify if user has access with his id to this planning
        if (optUser.isPresent() && optPlanning.isPresent()) {
            Optional<Share> optShare = shareService.getShareByPlanningAndUser(optPlanning.get(), optUser.get());
            if (optShare.isPresent()) {
                Planning planning = optPlanning.get();
                planning = planningService.updatePlanningName(planning, name);

                PlanningDTO planningDTO = new PlanningDTO();
                planningDTO.setIdPlanning(planning.getIdPlanning());
                planningDTO.setNamePlanning(planning.getNamePlanning());
                planningDTO.setDateCreated(planning.getDateCreated());
                planningDTO.setIdOwner(planning.getUser().getIdUser());
                planningDTO.setReadOnly(optShare.get().getIsReadOnly());

                return ResponseEntity.status(HttpStatus.OK).body(planningDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new share
    // Verify if this request comes from the owner
    @PutMapping("/planning/new_share")
    public ResponseEntity<PlanningDTO> addNewShareToPlanning(@RequestBody ShareDTO shareDTO) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete existing share
    // Verify if this request comes from the owner
    //// MAYBE CREATE A CONTROLLER FOR SHARE ??
    @DeleteMapping("/planning/new_share")
    public ResponseEntity<PlanningDTO> deleteShareFromPlanning(@RequestBody ShareDTO shareDTO) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }




}
