package com.sbr.journalapp.controller;

import com.sbr.journalapp.entity.JournalEntry;
import com.sbr.journalapp.entity.User;
import com.sbr.journalapp.service.JournalEntryService;
import com.sbr.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-user-journal-details")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if(journalEntries != null && !journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("add-entry")
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.save(journalEntry, username);
        return true;
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId journalId){
        if(journalEntryService.findById(journalId).isPresent()){
            JournalEntry journalEntry = journalEntryService.findById(journalId).get();
//          return ResponseEntity.ok(journalEntryService.findById(journalId).get());
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }else{
//            return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        return journalEntryService.findById(journalId).orElse(null);
    }

    @GetMapping("/id")
    public ResponseEntity<JournalEntry> getJournalByIdv2(@RequestParam ObjectId journalId){
        if(journalEntryService.findById(journalId).isPresent()){
            return ResponseEntity.ok(journalEntryService.findById(journalId).get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId journalId){
        if(journalEntryService.findById(journalId).isPresent()){
            journalEntryService.deleteById(journalId);
            return ResponseEntity.ok("Deleted journal entry");
        }else{
            return ResponseEntity.ok("Journal entry not found");
        }
    }

    @PutMapping("/id")
    public ResponseEntity<JournalEntry> updateEntry(@RequestParam String username, @RequestBody JournalEntry journalEntry){
        if(journalEntryService.findById(journalEntry.getId()).isPresent()){
            JournalEntry old = journalEntryService.findById(journalEntry.getId()).get();
            if(journalEntry.getTitle() != null && !journalEntry.getTitle().equals(old.getTitle())){
                old.setTitle(journalEntry.getTitle());
            }
            if(journalEntry.getContent() != null && !journalEntry.getContent().equals(old.getContent())){
                old.setContent(journalEntry.getContent());
            }
            journalEntryService.save(journalEntry, username);
            return ResponseEntity.ok().body(journalEntry);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
