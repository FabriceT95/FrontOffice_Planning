package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsersDTO {

    public UsersDTO() {
    }

    private Long idUser;

    @NotNull(message = "UsersDTO must have a username")
    @Size(min = 3, message = "user name should have at least 3 characters")
    private String username;

    @Email
    private String email;


    private String password;


    private String photo;

    @DateTimeFormat
    private LocalDateTime dateLastLogin;


    private List<RoleDTO> roleDTOList = new ArrayList<>();

    private AddressDTO addressDTO;

    private Long planningId;

    private List<Long> sharedPlanningId = new ArrayList<>();


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(LocalDateTime dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }

    public List<RoleDTO> getRoleDTOList() {
        return roleDTOList;
    }

    public void setRoleDTOList(List<RoleDTO> roleDTOList) {
        this.roleDTOList = roleDTOList;
    }

    public void addRoleDTO(RoleDTO roleDTO) {
        this.roleDTOList.add(roleDTO);
    }

}
