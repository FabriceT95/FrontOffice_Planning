package com.agendo.frontoffice_planning.controller.exception.share;

public class ShareAlreadyExistsException extends Exception {
    public ShareAlreadyExistsException(String email, String planningname) {
        super(planningname + " is already shared with " + email);
    }

    public ShareAlreadyExistsException(String email, long planningId) {
        super("Planning with id "+planningId+" is already shared with " + email);
    }
}
