package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ShareDTO {

    @NotNull(message = "Share DTO must have a planningId")
    private Long planningId;

    @NotNull(message = "Share DTO must have an email")
    private String email;

    @NotNull(message = "Share DTO must have a boolean isReadOnly")
    private boolean isReadOnly;

    public ShareDTO() {
    }

    public Long getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Long planningId) {
        this.planningId = planningId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}
