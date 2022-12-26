package com.example.frontoffice_planning.controller;


import com.example.frontoffice_planning.controller.exception.*;
import com.example.frontoffice_planning.controller.models.*;
import com.example.frontoffice_planning.entity.*;
import com.example.frontoffice_planning.service.EventService;
import com.example.frontoffice_planning.service.PlanningService;
import com.example.frontoffice_planning.service.ShareService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class PlanningController {


    private final PlanningService planningService;

    private final ShareService shareService;

    private final EventService eventService;

    // private final EventRepository eventRepository;

    public PlanningController(PlanningService planningService, ShareService shareService, EventService eventService) {
        this.planningService = planningService;
        this.shareService = shareService;
        this.eventService = eventService;
    }

    /**
     * Retrieve the Owner planning.
     *
     * @param users   Getting authenticated user from the auth filter
     * @param userDTO simple representation of a User
     * @return planningDTO Planning object with all tasks and basic attributes
     */
    @GetMapping("/planning")
    public ResponseEntity<PlanningDTO> getPlanningById(@RequestAttribute("user") Users users, @Valid @RequestBody UsersDTO userDTO) {
        try {
            PlanningDTO planningDTO = planningService.getPlanningByOwner(userDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(planningDTO);
        } catch (UserNotOwnerException | UserNotMatchShareRequest e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    // Maybe allowing the owner to get by this path aswell ?
    // For now, only shared people can go this way, not the owner
    // Owner only gets it from "UserControlle" API

    /**
     * Get a planning shared to a user. ShareDTO validates the share
     * Validation needs to be proven again for security purpose
     *
     * @param shareDTO key object attesting sharing request
     * @param users    Getting authenticated user from the auth filter
     * @return PlanningDTO Planning object with all tasks and basic attributes
     */
    @GetMapping("/planning/shared")
    public ResponseEntity<PlanningDTO> getPlanningByIdShare(@RequestAttribute("user") Users users, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            PlanningDTO planningDTO = planningService.getPlanningShared(shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(planningDTO);
        } catch (ShareNotFoundException | UserNotMatchShareRequest e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    /**
     * Update the planning itself. Only affects its name, only this field is editable
     *
     * @param name     New string value given to the planning
     * @param usersDTO simple representation of a User
     * @param users    Getting authenticated user from the auth filter
     * @return PlanningDTO Planning object with all tasks and basic attributes
     */
    @PutMapping("/planning")
    public ResponseEntity<PlanningDTO> updatePlanningName(@RequestAttribute("user") Users users, @RequestParam String name, @RequestBody UsersDTO usersDTO) {
        try {
            PlanningDTO planningDTO = planningService.updatePlanningName(name, usersDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(planningDTO);
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Update the planning itself. Only affects its name, only this field is editable
     *
     * @param name     New string value given to the planning
     * @param users    Getting authenticated user from the auth filter
     * @param shareDTO key object attesting sharing request
     * @return PlanningDTO Planning object with all tasks and basic attributes
     */
    @PutMapping("/planning/share")
    public ResponseEntity<PlanningDTO> updatePlanningNameShare(@RequestAttribute("user") Users users, @RequestParam String name, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            PlanningDTO planningDTO = planningService.updatePlanningNameShared(name, shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(planningDTO);

        } catch (UserNotMatchShareRequest | ShareNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException | ShareReadOnlyException | UserNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Allows the owner to add a new user to his planning.
     * ShareDTO manages the readOnly value (can be set on update)
     *
     * @param shareDTO key object attesting sharing request
     * @param users    Getting authenticated user from the auth filter
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @PostMapping("/share")
    public ResponseEntity<ShareDTO> addNewShareToPlanning(@RequestAttribute("user") Users users, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            shareDTO = shareService.createShare(shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(shareDTO);
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ShareAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (PlanningNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Allows the owner to update an existing share from his planning.
     * readOnly boolean will be set => true = read only, false = all
     *
     * @param shareDTO key object attesting sharing request
     * @param users    Getting authenticated user from the auth filter
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @PutMapping("/share")
    public ResponseEntity<ShareDTO> updateShareFromPlanning(@RequestAttribute("user") Users users, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            shareService.updateShare(shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(shareDTO);
        } catch (PlanningNotFoundException | ShareNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    /**
     * Allows the owner to delete user from his planning.
     * With ShareDTO as body, we get the planning id and the user share-with id
     *
     * @param shareDTO key object attesting sharing request
     * @param users    Getting authenticated user from the auth filter
     * @return PlanningDTO Planning object with all tasks and basic attributes + updated list of share if success
     */
    @DeleteMapping("/share")
    public ResponseEntity<PlanningDTO> deleteShareFromPlanning(@RequestAttribute("user") Users users, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            shareService.deleteShare(shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PlanningNotFoundException | ShareNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    /**
     * Get all events from a particular planning.
     * Only owner and shared are allowed to retrieve data
     *
     * @param users   Getting authenticated user from the auth filter
     * @param nbStart Item index start
     * @param nbEnd   Item index end
     * @return List of eventDTO
     */
    @GetMapping("/planning/owner/events")
    public ResponseEntity<List<EventDTO>> getEventsFromOwnerPlanning(@RequestAttribute("user") Users users, @RequestParam("nb_start") int nbStart, @RequestParam("nb_end") int nbEnd) {
        try {
            List<EventDTO> eventDTOList = eventService.getEventsByPlanningId(users);
            return ResponseEntity.status(HttpStatus.OK).body(eventDTOList);
        } catch (UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Get all events from a particular planning.
     * Only owner and shared are allowed to retrieve data
     *
     * @param users    Getting authenticated user from the auth filter
     * @param shareDTO key object attesting sharing request
     * @param nbStart  Item index start
     * @param nbEnd    Item index end
     * @return List of eventDTO
     */

    @GetMapping("/planning/shared/events")
    public ResponseEntity<List<EventDTO>> getEventsFromOnePlanningShared(@RequestAttribute("user") Users users, @RequestParam("nb_start") int nbStart, @RequestParam("nb_end") int nbEnd, @Valid @RequestBody ShareDTO shareDTO) {
        try {
            List<EventDTO> eventDTOList = eventService.getEventsByPlanningIdShared(shareDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(eventDTOList);
        } catch (ShareNotFoundException | UserNotMatchShareRequest e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Get all events from all shared planning (and user's one)
     * Only owner and shared are allowed to retrieve data
     *
     * @param users Getting authenticated user from the auth filter
     * @return List of eventDTO
     */
    @GetMapping("/planning/events/all")
    public ResponseEntity<List<EventDTO>> getEventsFromSharedAndOwnerPlanning(@RequestAttribute("user") Users users) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents(users));

    }
}
