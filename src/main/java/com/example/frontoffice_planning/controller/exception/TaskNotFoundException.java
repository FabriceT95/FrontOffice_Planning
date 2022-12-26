package com.example.frontoffice_planning.controller.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String taskname) {
        super(taskname + " doesn't exist !");
    }

    public TaskNotFoundException(long id) {
        super("Task with id " + id + " doesn't exist !");
    }
}
