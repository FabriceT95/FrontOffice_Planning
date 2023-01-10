package com.agendo.frontoffice_planning.controller.exception.share;

public class ShareNotFoundException extends Exception {
    public ShareNotFoundException(String email, long planningId) {
        super("User with email (" + email + ") can not access to the planning with id " + planningId);
    }

}
