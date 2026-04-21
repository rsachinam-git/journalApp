package com.sbr.journalapp.controller;

import com.sbr.journalapp.entity.User;
import com.sbr.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;





    /*@DeleteMapping("/id/{journalId}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId journalId){
        if(journalEntryService.findById(journalId).isPresent()){
            journalEntryService.deleteById(journalId);
            return ResponseEntity.ok("Deleted journal entry");
        }else{
            return ResponseEntity.ok("Journal entry not found");
        }
    }*/

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb = userService.findByUsername(username);
        if(userInDb != null){
            userInDb.setPassword(user.getPassword());
            userService.save(userInDb);
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
