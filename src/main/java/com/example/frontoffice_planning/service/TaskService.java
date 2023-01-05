package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.*;
import com.example.frontoffice_planning.controller.models.ActionDTO;
import com.example.frontoffice_planning.controller.models.EventDTO;
import com.example.frontoffice_planning.controller.models.Share.ShareDTO;
import com.example.frontoffice_planning.controller.models.TaskDTO;
import com.example.frontoffice_planning.entity.*;
import com.example.frontoffice_planning.repository.ActionRepository;
import com.example.frontoffice_planning.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PlanningService planningService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ShareService shareService;

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO, Planning planning, Users users) throws PlanningNotFoundException, UserNotFoundException, ActionNotFoundException {

        Optional<Action> optAction = actionRepository.findById(1L);
        if (optAction.isEmpty()) {
            throw new ActionNotFoundException(1L);
        }

        Task newTask = new Task();

        newTask.setNameTask(taskDTO.getNameTask());
        newTask.setDescription(taskDTO.getDescription());
        newTask.setDateTaskStart(taskDTO.getDateTaskStart());
        newTask.setDateTaskEnd(taskDTO.getDateTaskEnd());
        newTask.setDateCreated(LocalDateTime.now());
        newTask.setPlanning(planning);
        Task createdTask = taskRepository.save(newTask);

        Event event = new Event(LocalDateTime.now(), optAction.get());
        event.setPlanning(planning);
        event.setUser(users);
        createdTask.addEvent(event);
        event.setTask(createdTask);
        eventService.createEvent(event);
        taskRepository.save(createdTask);

        System.out.println("Task " + createdTask.getIdTask() + " is saved");

        taskDTO.setDateCreated(createdTask.getDateCreated());
        taskDTO.setIdTask(createdTask.getIdTask());
        taskDTO.setEventList(newTask.getEventsByIdTask().stream().map(e -> {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setIdPlanning(e.getPlanning().getIdPlanning());
            eventDTO.setDateCreated(e.getDateCreated());
            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setIdAction(e.getAction().getIdAction());
            actionDTO.setName(e.getAction().getName());
            eventDTO.setActionDTO(actionDTO);
            eventDTO.setIdEvent(e.getIdEvent());
            return eventDTO;
        }).collect(Collectors.toList()));
        return taskDTO;
    }

    public TaskDTO getTaskDTOById(long id, Users users) throws TaskNotFoundException, UserNotOwnerException {

        Optional<Task> searchedTask = taskRepository.findById(id);
        if (searchedTask.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        Task task = searchedTask.get();

        if (!users.getPlanning().equals(task.getPlanningByIdPlanning())) {
            throw new UserNotOwnerException(users.getUsername(), task.getPlanningByIdPlanning().getNamePlanning());
        }


        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setIdTask(task.getIdTask());
        taskDTO.setIdPlanning(task.getPlanningByIdPlanning().getIdPlanning());
        taskDTO.setNameTask(task.getNameTask());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDateCreated(task.getDateCreated());
        taskDTO.setDateTaskStart(task.getDateTaskStart());
        taskDTO.setDateTaskEnd(task.getDateTaskEnd());
        // Issue : Events has a relationship with Users, Planning and Task
        // => Each events are in three copies
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
        return taskDTO;
    }

    public TaskDTO getTaskDTOByIdShared(long id, ShareDTO shareDTO, Users users) throws TaskNotFoundException, PlanningNotFoundException, UserNotFoundException, ShareNotFoundException {

        Planning planning = planningService.getPlanningById(shareDTO.getPlanningId());
        Optional<Users> optUsers = userService.getUserById(shareDTO.getUserId());
        if (optUsers.isEmpty()) throw new UserNotFoundException(shareDTO.getUserId());
        shareService.shareExists(planning, users);

        Optional<Task> searchedTask = taskRepository.findById(id);
        if (searchedTask.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        Task task = searchedTask.get();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setIdTask(task.getIdTask());
        taskDTO.setIdPlanning(task.getPlanningByIdPlanning().getIdPlanning());
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
        return taskDTO;
    }

    public TaskDTO updateTask(TaskDTO taskDTO, Users users) throws TaskNotFoundException, UserNotFoundException, ActionNotFoundException, PlanningNotFoundException {

        Planning planning = planningService.getPlanningById(taskDTO.getIdPlanning());

        Optional<Task> optTask = taskRepository.findById(taskDTO.getIdTask());
        if (optTask.isEmpty()) {
            throw new TaskNotFoundException(taskDTO.getIdTask());
        }

        Optional<Action> optAction = actionRepository.findById(2L);
        if (optAction.isEmpty()) {
            throw new ActionNotFoundException(2L);
        }

        Task task = optTask.get();
        task.setNameTask(taskDTO.getNameTask());
        task.setDateTaskStart(taskDTO.getDateTaskStart());
        task.setDateTaskEnd(taskDTO.getDateTaskEnd());
        task.setDescription(taskDTO.getDescription());
        Event event = new Event();
        event.setTask(task);
        event.setDateCreated(LocalDateTime.now());
        event.setPlanning(planning);
        // Need to get the user
        event.setUser(users);
        event.setAction(actionRepository.findById(2L).get());

        Event savedEvent = eventService.createEvent(event);

        task.addEvent(savedEvent);

        // TODO : password
        taskRepository.save(task);
        System.out.println("Task " + task.getIdTask() + " is updated");

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

        return taskDTO;

    }

    @Transactional
    public void delete(long id, Users users) throws TaskNotFoundException, UserNotFoundException, UserNotOwnerException {

        Optional<Task> taskToDelete = taskRepository.findById(id);

        if (taskToDelete.isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        if (!users.getPlanning().equals(taskToDelete.get().getPlanningByIdPlanning())) {
            throw new UserNotOwnerException(users.getUsername(), taskToDelete.get().getPlanningByIdPlanning().getNamePlanning());
        }

        taskRepository.delete(taskToDelete.get());

        System.out.println("Task " + id + " has been deleted");
    }

    @Transactional
    public void deleteShared(long id, ShareDTO shareDTO, Users users) throws TaskNotFoundException, UserNotFoundException, PlanningNotFoundException, ShareNotFoundException, ShareReadOnlyException {

        Optional<Users> optUsers = userService.getUserById(users.getIdUser());
        if (optUsers.isEmpty()) {
            throw new UserNotFoundException(users.getIdUser());
        }

        Planning planning = planningService.getPlanningById(shareDTO.getPlanningId());
        shareService.shareExists(planning, users);
        shareService.isFullAccess(planning, users);
        Optional<Task> taskToDelete = taskRepository.findById(id);
        if (taskToDelete.isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.delete(taskToDelete.get());

        System.out.println("Task " + id + " from shared Planning " + shareDTO.getPlanningId() + " has been deleted");
    }
}
