package com.agendo.frontoffice_planning.controller;

import com.agendo.frontoffice_planning.controller.exception.action.ActionNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.share.ShareAlreadyExistsException;
import com.agendo.frontoffice_planning.controller.exception.share.ShareNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.share.ShareReadOnlyException;
import com.agendo.frontoffice_planning.controller.exception.storage.StorageFileNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.task.TaskNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.user.*;
import com.agendo.frontoffice_planning.controller.exception.planning.PlanningNotFoundException;
import com.agendo.frontoffice_planning.controller.exception.task.TaskFailedSaveException;
import com.agendo.frontoffice_planning.controller.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfe) {
        List<String> errors = Collections.singletonList(unfe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException (UserAlreadyExistException uaee) {
        List<String> errors = Collections.singletonList(uaee.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotMatchShareRequest.class)
    public ResponseEntity<ErrorResponse> handleUserNotMatchShareRequestException (UserNotMatchShareRequest unmsr) {
        List<String> errors = Collections.singletonList(unmsr.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<ErrorResponse> handleUserNotOwnerException (UserNotOwnerException unoe) {
        List<String> errors = Collections.singletonList(unoe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserUpdateDeniedException.class)
    public ResponseEntity<ErrorResponse> handleUserUpdateDeniedException (UserUpdateDeniedException uude) {
        List<String> errors = Collections.singletonList(uude.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskFailedSaveException.class)
    public ResponseEntity<ErrorResponse> handleTaskFailedSaveException (TaskFailedSaveException tfse) {
        List<String> errors = Collections.singletonList(tfse.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException (TaskNotFoundException tnfe) {
        List<String> errors = Collections.singletonList(tnfe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorResponse("File too large!", HttpStatus.EXPECTATION_FAILED));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStorageFileNotFoundException(StorageFileNotFoundException sfnfe) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorResponse("File too large!", HttpStatus.EXPECTATION_FAILED));
    }

    @ExceptionHandler(ShareAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleShareAlreadyExistsException (ShareAlreadyExistsException saee) {
        List<String> errors = Collections.singletonList(saee.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShareNotFoundException (ShareNotFoundException snfe) {
        List<String> errors = Collections.singletonList(snfe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ShareReadOnlyException.class)
    public ResponseEntity<ErrorResponse> handleShareReadOnlyException (ShareReadOnlyException sroe) {
        List<String> errors = Collections.singletonList(sroe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PlanningNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlanningNotFoundException (PlanningNotFoundException pnfe) {
        List<String> errors = Collections.singletonList(pnfe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActionNotFoundException (ActionNotFoundException anfe) {
        List<String> errors = Collections.singletonList(anfe.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errors.get(0), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }




}
