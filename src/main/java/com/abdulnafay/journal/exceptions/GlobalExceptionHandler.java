package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.abdulnafay.journal.entities.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullUserDTOException.class)
    public ResponseEntity<ErrorResponse> handleNullUserDTOException(NullUserDTOException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateNewUserException.class)
    public ResponseEntity<ErrorResponse> handleCreateNewUserException(CreateNewUserException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateNewJournalEntryException.class)
    public ResponseEntity<ErrorResponse> handleCreateNewJournalEntryException(CreateNewJournalEntryException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateNewAdminException.class)
    public ResponseEntity<ErrorResponse> handleCreateNewAdminException(CreateNewAdminException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteJournalEntryException.class)
    public ResponseEntity<ErrorResponse> handleDeleteJournalEntryException(DeleteJournalEntryException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateUserException.class)
    public ResponseEntity<ErrorResponse> handleUpdateUserException(UpdateUserException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
