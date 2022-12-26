package com.example.frontoffice_planning.controller.exception;

public class UserNotMatchShareRequest extends Exception {
    public UserNotMatchShareRequest() {
        super("User Auth doesn't match with asker. User Auth should be equal to the one who ask.");
    }
}
