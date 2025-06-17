package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdateUserException extends RuntimeException {
    public UpdateUserException() {
        super("Faced an error while updating a user!");
    }
}
