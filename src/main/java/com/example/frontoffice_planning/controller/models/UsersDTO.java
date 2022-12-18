package com.example.frontoffice_planning.controller.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class UsersDTO {

    public UsersDTO() {
    }

    private Long idUser;

    private String pseudo;

    private String email;

    private String password;

    private String photo;

    private AddressDTO addressDTO;

    private Long planningId;

    private List<Long> sharedPlanningId = new ArrayList<>();


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }
    public Long getPlanningId() {
        return planningId;
    }

    public void setPlanningId(Long planningId) {
        this.planningId = planningId;
    }

    public List<Long> getSharedPlanningId() {
        return sharedPlanningId;
    }

    public void setSharedPlanningId(List<Long> sharedPlanningId) {
        this.sharedPlanningId = sharedPlanningId;
    }
}
