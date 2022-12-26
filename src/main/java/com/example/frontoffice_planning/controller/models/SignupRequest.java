package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotNull;

public class SignupRequest {

    @NotNull(message = "SignupRequest must have an username")
    private String username;

    @NotNull(message = "SignupRequest must have a password")
    private String password;

    @NotNull(message = "SignupRequest must have an email")
    private String email;

    private String postalCode;

    private String city;

    public SignupRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
