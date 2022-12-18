package com.example.frontoffice_planning.controller.models;

public class ShareDTO {

    private Long planningId;

    private Long userId;

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
