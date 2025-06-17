package com.abdulnafay.journal.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abdulnafay.journal.entities.JournalEntry;
import com.abdulnafay.journal.entities.JournalEntryDTO;
import com.abdulnafay.journal.entities.User;
import com.abdulnafay.journal.exceptions.CreateNewJournalEntryException;
import com.abdulnafay.journal.exceptions.DeleteJournalEntryException;
import com.abdulnafay.journal.repositories.JournalEntryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    @Autowired
    public JournalEntryService(JournalEntryRepository jEntryRepository, UserService uService) {
        journalEntryRepository = jEntryRepository;
        userService = uService;
    }

    @Transactional
    public void saveEntry(JournalEntryDTO journalEntryDTO, String userName) {
        try {
            User user = userService.findByUserName(userName);
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setTitle(journalEntryDTO.getTitle());
            journalEntry.setContent(journalEntryDTO.getContent());
            journalEntry.setDateTime(LocalDateTime.now());
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Error encountered while saving Entry: ", e);
            throw new CreateNewJournalEntryException();
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user.getJournalEntries().removeIf(x -> x.getId().equals(id))) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new DeleteJournalEntryException();
        }
    }
}
