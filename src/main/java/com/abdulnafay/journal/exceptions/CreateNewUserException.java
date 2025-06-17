package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateNewUserException extends RuntimeException {
    public CreateNewUserException() {
        super("Faced an error while creating a new user!");
    }
}
