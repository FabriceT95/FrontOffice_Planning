package com.example.frontoffice_planning.controller.exception.user;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String username) {
        super(username + "already exist in database");
    }
}
