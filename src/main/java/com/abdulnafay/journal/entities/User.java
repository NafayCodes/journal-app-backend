package com.abdulnafay.journal.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users") // For an SQL database, we'd write @Table(name = "users")
@Data
@NoArgsConstructor
public class User { // For an SQL database, we'd also have a @Column(name = "Column Name")
                    // annotation with each field as well to tell the name of the column
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @Nonnull
    private String userName;
    @Nonnull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}
