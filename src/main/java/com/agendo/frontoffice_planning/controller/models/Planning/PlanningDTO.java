package com.agendo.frontoffice_planning.controller.models.Planning;

import com.agendo.frontoffice_planning.controller.models.Share.ShareDTO;
import com.agendo.frontoffice_planning.controller.models.TaskDTO;
import com.agendo.frontoffice_planning.controller.models.User.UsersDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanningDTO {

    private long idPlanning;

    @NotNull(message = "PlanningDTO must be associated with a UsersDTO")
    private UsersDTO usersDTO;

    @NotNull(message = "PlanningDTO must have a name")
    @Size(min = 3, message = "planning name should have at least 3 characters")
    private String namePlanning;

    @DateTimeFormat
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

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
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
