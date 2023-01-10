package com.agendo.frontoffice_planning.controller.exception.user;

public class UserNotMatchShareRequest extends Exception {
    public UserNotMatchShareRequest() {
        super("User Auth doesn't match with asker. User Auth should be equal to the one who ask.");
    }
}
