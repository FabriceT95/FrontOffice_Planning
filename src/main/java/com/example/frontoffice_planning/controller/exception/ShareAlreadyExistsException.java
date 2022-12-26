package com.example.frontoffice_planning.controller.exception;

public class ShareAlreadyExistsException extends Exception {
    public ShareAlreadyExistsException(String email, String planningname) {
        super(planningname + " is already shared with " + email);
    }
}
