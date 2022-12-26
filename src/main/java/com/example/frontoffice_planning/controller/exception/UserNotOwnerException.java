package com.example.frontoffice_planning.controller.exception;

public class UserNotOwnerException extends Exception{
    public UserNotOwnerException(String username, String planningname) {
        super(username + " is not owner of planning called " +planningname);
    }
}
