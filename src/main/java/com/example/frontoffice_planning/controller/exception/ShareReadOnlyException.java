package com.example.frontoffice_planning.controller.exception;

public class ShareReadOnlyException extends Exception{
    public ShareReadOnlyException(String email, long planningId) {
        super("User with email (" + email + ") is currently only allowed to read data on the planning with id " + planningId);
    }
}
