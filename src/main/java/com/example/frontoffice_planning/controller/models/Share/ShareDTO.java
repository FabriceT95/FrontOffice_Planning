package com.example.frontoffice_planning.controller.models.Share;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ShareDTO {

    @NotNull(message = "Share DTO must have a planningId")
    private Long planningId;

    @NotNull(message = "Share DTO must have an userId")
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}
