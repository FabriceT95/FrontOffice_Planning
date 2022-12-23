package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private Long idTask;

    @NotEmpty
    private Long idPlanning;

    @NotEmpty
    @Size(min = 3, message = "user name should have at least 3 characters")
    private String nameTask;


    @DateTimeFormat
    private LocalDateTime dateCreated;

    @NotEmpty
    @DateTimeFormat

    private LocalDateTime dateTaskStart;

    @NotEmpty
    @DateTimeFormat
    private LocalDateTime dateTaskEnd;

    private String description;


    private List<EventDTO> eventList = new ArrayList<>();

    public TaskDTO() {
    }

    public long getIdTask() {
        return idTask;
    }
    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateTaskStart() {
        return dateTaskStart;
    }

    public void setDateTaskStart(LocalDateTime dateTaskStart) {
        this.dateTaskStart = dateTaskStart;
    }

    public LocalDateTime getDateTaskEnd() {
        return dateTaskEnd;
    }

    public void setDateTaskEnd(LocalDateTime dateTaskEnd) {
        this.dateTaskEnd = dateTaskEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EventDTO> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDTO> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(EventDTO event) {
        this.eventList.add(event);
    }
}
