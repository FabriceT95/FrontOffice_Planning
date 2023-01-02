package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.*;
import com.example.frontoffice_planning.controller.models.ActionDTO;
import com.example.frontoffice_planning.controller.models.EventDTO;
import com.example.frontoffice_planning.controller.models.ShareDTO;
import com.example.frontoffice_planning.entity.Event;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final PlanningService planningService;
    private final UserService userService;

    private final ShareService shareService;

    public EventService(EventRepository eventRepository, PlanningService planningService, UserService userService, ShareService shareService) {
        this.eventRepository = eventRepository;
        this.planningService = planningService;
        this.userService = userService;
        this.shareService = shareService;
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<EventDTO> getEventsByPlanningId(Users users) throws PlanningNotFoundException, UserNotOwnerException {
        Planning planning = users.getPlanning();
        if (!Objects.equals(planning.getUser().getEmail(), users.getEmail())) {
            throw new UserNotOwnerException(users.getUsername(), planning.getNamePlanning());
        }

        List<Event> eventList = eventRepository.findTop10EventsByPlanningIdPlanningOrderByDateCreatedDesc(planning.getIdPlanning());

        return eventList.stream().map(event -> {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setIdEvent(event.getIdEvent());
            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setIdAction(event.getAction().getIdAction());
            actionDTO.setName(event.getAction().getName());
            eventDTO.setActionDTO(actionDTO);
            eventDTO.setIdPlanning(planning.getIdPlanning());
            eventDTO.setDateCreated(event.getDateCreated());
            eventDTO.setUsername(event.getUser().getUsername());
            eventDTO.setPlanningName(event.getPlanning().getNamePlanning());
            return eventDTO;
        }).toList();
    }

    public List<EventDTO> getEventsByPlanningIdShared(ShareDTO shareDTO, Users users) throws PlanningNotFoundException, UserNotMatchShareRequest, UserNotFoundException, ShareNotFoundException {

        if (!Objects.equals(shareDTO.getUserId(), users.getIdUser())) {
            throw new UserNotMatchShareRequest();
        }

        Planning planning = planningService.getPlanningById(shareDTO.getPlanningId());

        Optional<Users> optUser = userService.getUserById(shareDTO.getUserId());

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(shareDTO.getUserId());
        }

        Share share = shareService.getShareByPlanningAndUser(planning, optUser.get());

        List<Event> eventList = eventRepository.findTop10EventsByPlanningIdPlanningOrderByDateCreatedDesc(planning.getIdPlanning());

        return eventList.stream().map(event -> {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setIdEvent(event.getIdEvent());
            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setIdAction(event.getAction().getIdAction());
            actionDTO.setName(event.getAction().getName());
            eventDTO.setActionDTO(actionDTO);
            eventDTO.setIdPlanning(planning.getIdPlanning());
            eventDTO.setDateCreated(event.getDateCreated());
            eventDTO.setUsername(event.getUser().getUsername());
            eventDTO.setPlanningName(event.getPlanning().getNamePlanning());
            return eventDTO;
        }).toList();
    }

    public List<EventDTO> getAllEvents(Users users) {
        List<Planning> planningList = new ArrayList<>() {
            {
                add(users.getPlanning());
            }
        };

        List<Share> shareList = shareService.findAllByUsers(users);

        if (!shareList.isEmpty()) {
            shareList.stream().map(Share::getPlanning).forEachOrdered(planningList::add);
        }
        List<Event> eventList = eventRepository.findTop10ByPlanningInOrderByDateCreatedDesc(planningList);
        return eventList.stream().map(event -> {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setIdEvent(event.getIdEvent());
            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setIdAction(event.getAction().getIdAction());
            actionDTO.setName(event.getAction().getName());
            eventDTO.setActionDTO(actionDTO);
            eventDTO.setIdPlanning(event.getPlanning().getIdPlanning());
            eventDTO.setDateCreated(event.getDateCreated());
            eventDTO.setUsername(event.getUser().getUsername());
            eventDTO.setPlanningName(event.getPlanning().getNamePlanning());
            return eventDTO;
        }).toList();
    }
}
