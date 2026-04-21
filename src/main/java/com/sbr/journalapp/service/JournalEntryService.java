package com.sbr.journalapp.service;

import com.sbr.journalapp.entity.JournalEntry;
import com.sbr.journalapp.entity.User;
import com.sbr.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void save(JournalEntry journalEntry, String username) {

        try {
            User user = userService.findByUsername(username);
            JournalEntry journalEntrySaved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(journalEntrySaved);
//            user.setUsername(null);
            userService.save(user);
        }catch (Exception e){
            throw new RuntimeException("Error saving journal entry"+e);
        }
    }

    public List<JournalEntry> findAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
