package com.example.frontoffice_planning.controller.exception;

public class TaskFailedSaveException extends Exception {
    public TaskFailedSaveException(String taskname) {
        super(taskname + " has not been created : Save failed !");
    }
}
