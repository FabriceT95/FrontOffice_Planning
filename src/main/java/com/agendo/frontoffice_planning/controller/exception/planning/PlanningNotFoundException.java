package com.agendo.frontoffice_planning.controller.exception.planning;

public class PlanningNotFoundException extends Exception {
    public PlanningNotFoundException(String planningname) {
        super(planningname + " doesn't exist ! !");
    }

    public PlanningNotFoundException(long id) {
        super("Planning with id " + id + " doesn't exist !");
    }

}
