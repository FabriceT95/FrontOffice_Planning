package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.*;
import com.example.frontoffice_planning.controller.models.PlanningDTO;
import com.example.frontoffice_planning.controller.models.ShareDTO;
import com.example.frontoffice_planning.controller.models.TaskDTO;
import com.example.frontoffice_planning.controller.models.UsersDTO;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.Task;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.PlanningRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

    @Transactional
    public Planning createPlanning(String name) {
        Planning planning = new Planning(name + "'s planning", LocalDateTime.now());
        return planningRepository.save(planning);
    }

    @Transactional
    public Planning save(Planning planning) {
        return planningRepository.save(planning);
    }

    public Planning getPlanningById(long id) throws PlanningNotFoundException {
        Optional<Planning> optPlanning = planningRepository.findById(id);
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(id);
        }
        return optPlanning.get();
    }

    public PlanningDTO getPlanningByOwner(Users users) throws PlanningNotFoundException {

       /* if (!Objects.equals(usersDTO.getEmail(), users.getEmail())) {
            throw new UserNotMatchShareRequest();
        }

        Optional<Planning> optPlanning = planningRepository.findById(usersDTO.getPlanningId());
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(usersDTO.getPlanningId());
        }
        if (!optPlanning.get().getUser().getIdUser().equals(usersDTO.getIdUser())) {
            throw new UserNotOwnerException(usersDTO.getUsername(), optPlanning.get().getNamePlanning());
        }*/

        // Planning planning = optPlanning.get();
        //Planning planning = users.getPlanning();
        Planning planning = getPlanningById(users.getPlanning().getIdPlanning());
        PlanningDTO planningDTO = new PlanningDTO();
        planningDTO.setIdPlanning(planning.getIdPlanning());
        planningDTO.setNamePlanning(planning.getNamePlanning());
        planningDTO.setDateCreated(planning.getDateCreated());

        // planningDTO.setUsersDTO(usersDTO);
        planningDTO.setReadOnly(false);

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

        return planningDTO;

    }

    public PlanningDTO getPlanningShared(ShareDTO shareDTO, Users users) throws PlanningNotFoundException, UserNotFoundException, ShareNotFoundException, UserNotMatchShareRequest {

        if (!Objects.equals(shareDTO.getEmail(), users.getEmail())) {
            throw new UserNotMatchShareRequest();
        }

        Planning planning = getPlanningById(shareDTO.getPlanningId());
        Optional<Users> optUser = userService.getUserByEmail(shareDTO.getEmail());

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(shareDTO.getEmail());
        }

        Share share = shareService.getShareByPlanningAndUser(planning, optUser.get());

        PlanningDTO planningDTO = new PlanningDTO();
        planningDTO.setIdPlanning(planning.getIdPlanning());
        planningDTO.setNamePlanning(planning.getNamePlanning());
        planningDTO.setDateCreated(planning.getDateCreated());

        // We can retrieve everything but we don't need all here. Just few data for display purpose.
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setPlanningId(planningDTO.getIdPlanning());
        usersDTO.setIdUser(planning.getUser().getIdUser());
        usersDTO.setPhoto(planning.getUser().getPhoto());
        usersDTO.setUsername(planning.getUser().getUsername());

        planningDTO.setUsersDTO(usersDTO);
        planningDTO.setReadOnly(share.getIsReadOnly());

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

        return planningDTO;


    }

    public PlanningDTO updatePlanningName(String name, UsersDTO usersDTO, Users users) throws UserNotOwnerException, PlanningNotFoundException {
        System.out.println("Planning " + usersDTO.getPlanningId() + " is being updated...");
        Planning planning = getPlanningById(usersDTO.getPlanningId());
        if (!Objects.equals(usersDTO.getEmail(), users.getEmail())) {
            throw new UserNotOwnerException(users.getUsername(), planning.getNamePlanning());
        }

        planning.setNamePlanning(name);
        planningRepository.save(planning);

        System.out.println("Planning " + usersDTO.getPlanningId() + " has been updated successfully");

        PlanningDTO planningDTO = new PlanningDTO();
        planningDTO.setIdPlanning(planning.getIdPlanning());
        planningDTO.setNamePlanning(planning.getNamePlanning());
        planningDTO.setDateCreated(planning.getDateCreated());
        planningDTO.setReadOnly(false);

        usersDTO.setPlanningId(planningDTO.getIdPlanning());
        usersDTO.setIdUser(planning.getUser().getIdUser());
        usersDTO.setPhoto(planning.getUser().getPhoto());
        usersDTO.setUsername(planning.getUser().getUsername());

        planningDTO.setUsersDTO(usersDTO);
        return planningDTO;
    }

    public PlanningDTO updatePlanningNameShared(String name, ShareDTO shareDTO, Users users) throws PlanningNotFoundException, UserNotFoundException, UserNotMatchShareRequest, ShareNotFoundException, ShareReadOnlyException {
        System.out.println("Shared Planning " + shareDTO.getPlanningId() + " is being updated...");
        if (!Objects.equals(shareDTO.getEmail(), users.getEmail())) {
            throw new UserNotMatchShareRequest();
        }
        Planning planning = getPlanningById(shareDTO.getPlanningId());
        Optional<Users> optUser = userService.getUserByEmail(shareDTO.getEmail());

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(shareDTO.getEmail());
        }

        shareService.isFullAccess(planning, users);

        Share share = shareService.getShareByPlanningAndUser(planning, optUser.get());

        planning.setNamePlanning(name);
        planningRepository.save(planning);

        System.out.println("Shared Planning " + shareDTO.getPlanningId() + " has been updated successfully");

        PlanningDTO planningDTO = new PlanningDTO();
        planningDTO.setIdPlanning(planning.getIdPlanning());
        planningDTO.setNamePlanning(planning.getNamePlanning());
        planningDTO.setDateCreated(planning.getDateCreated());
        planningDTO.setReadOnly(share.getIsReadOnly());


        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setPlanningId(planningDTO.getIdPlanning());
        usersDTO.setIdUser(planning.getUser().getIdUser());
        usersDTO.setPhoto(planning.getUser().getPhoto());
        usersDTO.setUsername(planning.getUser().getUsername());

        planningDTO.setUsersDTO(usersDTO);
        return planningDTO;
    }
}
