package com.example.frontoffice_planning.controller.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String username) {
        super(username + "already exist in database");
    }
}
