package com.example.frontoffice_planning.controller.models;

import jakarta.validation.constraints.NotNull;

public class SigninRequest {

    @NotNull(message = "SigninRequest must have an email")
    private String email;

    @NotNull(message = "SigninRequest must have a password")
    private String password;

    public SigninRequest() {
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
}
