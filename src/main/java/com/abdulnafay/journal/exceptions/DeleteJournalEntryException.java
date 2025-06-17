package com.abdulnafay.journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeleteJournalEntryException extends RuntimeException {
    public DeleteJournalEntryException() {
        super("Faced an error while deleting an existing journal entry!");
    }
}
