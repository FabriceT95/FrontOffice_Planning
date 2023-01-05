package com.example.frontoffice_planning.controller.models.User;

import com.example.frontoffice_planning.controller.models.AddressDTO;
import com.example.frontoffice_planning.controller.models.RoleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserDTO {
    public UpdateUserDTO() {
    }

    private Long idUser;

    @NotNull(message = "UsersDTO must have a username")
    @Size(min = 3, message = "user name should have at least 3 characters")
    private String username;

    @Email
    private String email;


    private String password;


    private String postalCode;

    private String city;


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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
