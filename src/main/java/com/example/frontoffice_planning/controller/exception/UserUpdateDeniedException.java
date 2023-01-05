package com.example.frontoffice_planning.controller.exception;

public class UserUpdateDeniedException extends Exception {
    public UserUpdateDeniedException() {
        super("You are not owner of this account. Update denied !");
    }
}
