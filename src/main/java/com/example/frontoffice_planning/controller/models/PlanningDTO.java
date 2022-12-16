package com.example.frontoffice_planning.controller.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningDTO {

    private long idPlanning;

    private String namePlanning;

    private LocalDateTime dateCreated;

    private List<TaskDTO> taskList = new ArrayList<>();

    private List<ShareDTO> shareList = new ArrayList<>();

    public PlanningDTO() {
    }

    public long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public String getNamePlanning() {
        return namePlanning;
    }

    public void setNamePlanning(String namePlanning) {
        this.namePlanning = namePlanning;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
