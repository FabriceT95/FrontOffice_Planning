package com.agendo.frontoffice_planning.controller.models.Share;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SharedUsersDTO {

    public SharedUsersDTO() {
    }

    private Long idUser;

    @NotNull(message = "sharedUsersDTO must have a username")
    @Size(min = 3, message = "sharedUsersDTO name should have at least 3 characters")
    private String username;

    @Email
    private String email;

    private String photo;

    private Long planningId;

    private boolean isReadOnly;


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Long planningId) {
        this.planningId = planningId;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}
