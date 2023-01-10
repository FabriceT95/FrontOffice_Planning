package com.agendo.frontoffice_planning.controller.exception.user;

public class UserNotOwnerException extends Exception{
    public UserNotOwnerException(String username, String planningname) {
        super(username + " is not owner of planning called " +planningname);
    }

    public UserNotOwnerException(String username, long planningId) {
        super(username + " is not owner of planning id " +planningId);
    }
}
