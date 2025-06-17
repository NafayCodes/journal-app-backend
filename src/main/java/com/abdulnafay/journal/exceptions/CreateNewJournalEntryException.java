package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateNewJournalEntryException extends RuntimeException {
    public CreateNewJournalEntryException() {
        super("Faced an error while creating a new journal entry!");
    }
}
