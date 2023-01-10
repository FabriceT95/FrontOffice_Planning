package com.agendo.frontoffice_planning.controller.models.User;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GetSharedUsersDTO {

    @NotNull(message = "To retrieve shared people, idPlanning is needed")
    private Long idPlanning;

    private List<Long> sharedIdList;

    public GetSharedUsersDTO() {
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public List<Long> getSharedIdList() {
        return sharedIdList;
    }

    public void setShareIdList(List<Long> sharedIdList) {
        this.sharedIdList = sharedIdList;
    }
}
