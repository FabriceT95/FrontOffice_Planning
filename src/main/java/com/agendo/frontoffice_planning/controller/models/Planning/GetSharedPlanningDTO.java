package com.agendo.frontoffice_planning.controller.models.Planning;

import jakarta.validation.constraints.NotNull;

public class GetSharedPlanningDTO {
    @NotNull(message = "To retrieve shared planning, idPlanning is needed")
    private Long idPlanning;

    @NotNull(message = "To retrieve shared planning, idUser is needed")
    private Long idUser;

    public GetSharedPlanningDTO() {
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
