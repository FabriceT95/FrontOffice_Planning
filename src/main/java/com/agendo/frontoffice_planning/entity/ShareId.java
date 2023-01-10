package com.agendo.frontoffice_planning.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ShareId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long planningId;

    public ShareId(Long userId, Long planningId) {
        this.userId = userId;
        this.planningId = planningId;
    }

    public ShareId() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Long planningId) {
        this.planningId = planningId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userId.intValue();
        result = prime * result + planningId.intValue();
        return result;
    }




}
