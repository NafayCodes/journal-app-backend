package com.abdulnafay.journal.controllers;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdulnafay.journal.entities.JournalEntry;
import com.abdulnafay.journal.entities.JournalEntryDTO;
import com.abdulnafay.journal.entities.User;
import com.abdulnafay.journal.services.JournalEntryService;
import com.abdulnafay.journal.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEntry(@RequestBody JournalEntryDTO journalEntryDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(journalEntryDTO, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .toList();
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent())
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // In non-relational databases, cascade deletion is not a feature, which means
    // we will have to manually delete the deleted Journal from the User's list of
    // Journals
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<HttpStatus> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if (journalEntryService.deleteById(myId, userName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<HttpStatus> updateJournalEntryById(@RequestBody JournalEntryDTO journalEntryDTO,
            @PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .toList();
        if (!collect.isEmpty()) {
            if (collect.get(0).getTitle().equals(journalEntryDTO.getTitle()) &&
                    collect.get(0).getContent().equals(journalEntryDTO.getContent()))
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            if (!journalEntryDTO.getTitle().equals(""))
                collect.get(0).setTitle(journalEntryDTO.getTitle());
            if (!journalEntryDTO.getContent().equals("") && journalEntryDTO.getContent() != null)
                collect.get(0).setContent(journalEntryDTO.getContent());

            journalEntryService.saveEntry(collect.get(0));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
