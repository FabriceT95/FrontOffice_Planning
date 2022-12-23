package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;

public class ShareDTO {

    @NotEmpty
    private Long planningId;

    @NotEmpty
    private String email;

    @NotEmpty
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
