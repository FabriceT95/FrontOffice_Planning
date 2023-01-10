package com.agendo.frontoffice_planning.service;

import com.agendo.frontoffice_planning.controller.exception.share.ShareNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.user.UserNotMatchShareRequest;
import com.agendo.frontoffice_planning.controller.models.ActionDTO;
import com.agendo.frontoffice_planning.controller.models.EventDTO;
import com.agendo.frontoffice_planning.controller.models.Share.ShareDTO;
import com.agendo.frontoffice_planning.entity.Event;
import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Share;
import com.agendo.frontoffice_planning.entity.Users;
import com.agendo.frontoffice_planning.repository.EventRepository;
import com.agendo.frontoffice_planning.controller.exception.planning.PlanningNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.user.UserNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.user.UserNotOwnerException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PlanningService planningService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

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
