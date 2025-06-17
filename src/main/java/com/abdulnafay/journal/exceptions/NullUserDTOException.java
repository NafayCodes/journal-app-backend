package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullUserDTOException extends RuntimeException{
    public NullUserDTOException() {
        super("Neither the username nor the password can be null");
    }
}
