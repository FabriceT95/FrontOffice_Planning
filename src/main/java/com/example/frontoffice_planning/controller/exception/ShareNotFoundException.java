package com.example.frontoffice_planning.controller.exception;

public class ShareNotFoundException extends Exception {
    public ShareNotFoundException(String email, long planningId) {
        super("User with email (" + email + ") can not access to the planning with id " + planningId);
    }

}
