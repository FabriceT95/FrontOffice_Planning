package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class EventDTO {
    private Long idEvent;

    private Long idPlanning;

    @NotEmpty
    private String username;

    @NotEmpty
    private String planningName;

    @DateTimeFormat
    private LocalDateTime dateCreated;


    @NotEmpty
    private ActionDTO actionDTO;


    public EventDTO() {

    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public ActionDTO getActionDTO() {
        return actionDTO;
    }

    public void setActionDTO(ActionDTO actionDTO) {
        this.actionDTO = actionDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlanningName() {
        return planningName;
    }

    public void setPlanningName(String planningName) {
        this.planningName = planningName;
    }
}
