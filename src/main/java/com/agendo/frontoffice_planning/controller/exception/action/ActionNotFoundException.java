package com.agendo.frontoffice_planning.controller.exception.action;

public class ActionNotFoundException extends Exception {
    public ActionNotFoundException(String actionname) {
        super(actionname + " doesn't exist !");
    }

    public ActionNotFoundException(long id) {
        super("Action with id " + id + " doesn't exist!");
    }
}
