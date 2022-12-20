package com.example.frontoffice_planning.controller;


import com.example.frontoffice_planning.controller.models.PlanningDTO;
import com.example.frontoffice_planning.controller.models.ShareDTO;
import com.example.frontoffice_planning.controller.models.TaskDTO;
import com.example.frontoffice_planning.controller.models.UsersDTO;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.ShareRepository;
import com.example.frontoffice_planning.service.PlanningService;
import com.example.frontoffice_planning.service.ShareService;
import com.example.frontoffice_planning.service.UserService;
import jakarta.validation.Valid;
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
    private final ShareRepository shareRepository;

    public PlanningController(UserService userService, PlanningService planningService, ShareService shareService, ShareRepository shareRepository) {
        this.userService = userService;
        this.planningService = planningService;
        this.shareService = shareService;
        this.shareRepository = shareRepository;
    }

    // Maybe allowing the owner to get by this path aswell ?
    // For now, only shared people can go this way, not the owner
    // Owner only gets it from "UserControlle" API

    /**
     * Get a planning shared by a user. ShareDTO validates the share
     * Validation needs to be proven again for security purpose
     * @param shareDTO key object attesting sharing
     * @return PlanningDTO Planning object with all tasks and basic attributes
     */
    @GetMapping("/planning/auth")
    public ResponseEntity<PlanningDTO> getPlanningByShare(@Valid @RequestBody ShareDTO shareDTO) {
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

                // We can retrieve everything but we don't need all here. Just few data for display purpose.
                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setPlanningId(planningDTO.getIdPlanning());
                usersDTO.setIdUser(planning.getUser().getIdUser());
                usersDTO.setPhoto(planning.getUser().getPhoto());
                usersDTO.setPseudo(planning.getUser().getPseudo());

                planningDTO.setUsersDTO(usersDTO);
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

    /**
     * Update the planning itself. Only affects its name, only this field is editable
     * @param name New string value given to the planning
     * @param shareDTO key object attesting sharing
     * @return PlanningDTO Planning object with all tasks and basic attributes
     */
    @PutMapping("/planning/auth?name={name}")
    public ResponseEntity<PlanningDTO> updatePlanningName(@PathVariable("name") String name, @Valid @RequestBody ShareDTO shareDTO) {
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
                planningDTO.setReadOnly(optShare.get().getIsReadOnly());

                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setPlanningId(planningDTO.getIdPlanning());
                usersDTO.setIdUser(planning.getUser().getIdUser());
                usersDTO.setPhoto(planning.getUser().getPhoto());
                usersDTO.setPseudo(planning.getUser().getPseudo());

                planningDTO.setUsersDTO(usersDTO);

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
    // Returns a status or a PlanningDTO ? => We can add manually from front-end if success

    /**
     * Allows the owner to add a new user to his planning.
     * ShareDTO manages the readOnly value (can be set on update)
     * @param shareDTO key object attesting sharing
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @PutMapping("/planning/new_share")
    public ResponseEntity<ShareDTO> addNewShareToPlanning(@Valid @RequestBody ShareDTO shareDTO){
//        Optional<Users> optUser = userService.getUserById();
        Optional<Users> optUserToAdd = userService.getUserById(shareDTO.getUserId());
        Optional<Planning> optPlanning = planningService.getPlanningById(shareDTO.getPlanningId());
        // Need security : Verify if user has access with his id to this planning
        if (optUserToAdd.isPresent() && optPlanning.isPresent()) {
            Optional<Share> optShare = shareService.getShareByPlanningAndUser(optPlanning.get(), optUserToAdd.get());
            if (optShare.isEmpty()) {
                Share share = new Share(optPlanning.get(), optUserToAdd.get(), shareDTO.isReadOnly());
                optPlanning.get().addShare(share);
                planningService.save(optPlanning.get());
                return ResponseEntity.status(HttpStatus.OK).body(shareDTO);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete existing share
    // Verify if this request comes from the owner
    //// MAYBE CREATE A CONTROLLER FOR SHARE ??
    // Returns a status or a PlanningDTO ? => We can remove manually from front-end if success

    /**
     * Allows the owner to delete user from his planning.
     * With ShareDTO as body, we get the planning id and the user share-with id
     * @param shareDTO key object attesting sharing
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @DeleteMapping("/planning/share")
    public ResponseEntity<PlanningDTO> deleteShareFromPlanning(@Valid @RequestBody ShareDTO shareDTO) {
        Optional<Users> optUser = userService.getUserById(shareDTO.getUserId());
        Optional<Planning> optPlanning = planningService.getPlanningById(shareDTO.getPlanningId());
        // Need security : Verify if user has access with his id to this planning
        if (optUser.isPresent() && optPlanning.isPresent()) {
            Planning planning = optPlanning.get();
            Users user = optUser.get();
            Optional<Share> optShare = shareRepository.findByPlanningEqualsAndUsersEquals(planning, user);
            if (optShare.isPresent()) {
                shareService.delete(optShare.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Allows the owner to update an existing share from his planning.
     * readOnly boolean will be set => true = read only, false = all
     * @param shareDTO key object attesting sharing
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @PutMapping("/planning/update_share")
    public ResponseEntity<ShareDTO> updateShareFromPlanning(@Valid @RequestBody ShareDTO shareDTO) {
        Optional<Users> optUser = userService.getUserById(shareDTO.getUserId());
        Optional<Planning> optPlanning = planningService.getPlanningById(shareDTO.getPlanningId());
        // Need security : Verify if user has access with his id to this planning
        if (optUser.isPresent() && optPlanning.isPresent()) {
            Planning planning = optPlanning.get();
            Users user = optUser.get();
            Optional<Share> optShare = shareRepository.findByPlanningEqualsAndUsersEquals(planning, user);
            if (optShare.isPresent()) {
                optShare.get().setIsReadOnly(shareDTO.isReadOnly());
                shareService.save(optShare.get());
                return ResponseEntity.status(HttpStatus.OK).body(shareDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




}
