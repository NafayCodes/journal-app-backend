package com.abdulnafay.journal.entities;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JournalEntryDTO {
    @Nonnull
    private String title;
    private String content;
}
