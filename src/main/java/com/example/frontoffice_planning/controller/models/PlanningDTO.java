package com.example.frontoffice_planning.controller.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningDTO {

    private long idPlanning;

    private long idOwner;

    private String namePlanning;

    private LocalDateTime dateCreated;

    private boolean isReadOnly;

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

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
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

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDTO> taskList) {
        this.taskList = taskList;
    }

    public List<ShareDTO> getShareList() {
        return shareList;
    }

    public void setShareList(List<ShareDTO> shareList) {
        this.shareList = shareList;
    }
}
