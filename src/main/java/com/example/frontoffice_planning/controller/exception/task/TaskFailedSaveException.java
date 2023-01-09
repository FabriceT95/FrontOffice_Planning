package com.example.frontoffice_planning.controller.exception.task;

public class TaskFailedSaveException extends Exception {
    public TaskFailedSaveException(String taskname) {
        super(taskname + " has not been created : Save failed !");
    }
}
