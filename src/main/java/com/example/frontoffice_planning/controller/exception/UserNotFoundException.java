package com.example.frontoffice_planning.controller.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super(username + " doesn't exist !");
    }
}
