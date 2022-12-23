package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;

public class ActionDTO {
    private Long idAction;

    @NotEmpty
    private String name;

    public ActionDTO() {
    }

    public Long getIdAction() {
        return idAction;
    }

    public void setIdAction(Long idAction) {
        this.idAction = idAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
