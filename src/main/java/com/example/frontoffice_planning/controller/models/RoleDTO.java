package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotEmpty;

public class RoleDTO {

    private Long idRole;

    @NotEmpty
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
