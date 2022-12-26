package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {

    private Long idRole;

    @NotNull(message = "RoleDTO must have a name")
    private String name;

    public RoleDTO() {
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
