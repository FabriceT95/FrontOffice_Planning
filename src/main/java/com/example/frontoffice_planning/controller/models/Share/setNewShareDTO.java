package com.example.frontoffice_planning.controller.models.Share;

import jakarta.validation.constraints.NotNull;

public class setNewShareDTO {
    @NotNull(message = "New Share DTO must have a planningId")
    private Long planningId;

    @NotNull(message = "New Share DTO must have an email")
    private String email;

    public setNewShareDTO() {
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
}
