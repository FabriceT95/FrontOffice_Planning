package com.agendo.frontoffice_planning.controller.exception.user;

public class UserUpdateDeniedException extends Exception {
    public UserUpdateDeniedException() {
        super("You are not owner of this account. Update denied !");
    }
}
