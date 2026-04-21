package com.sbr.journalapp.controller;

import com.sbr.journalapp.entity.User;
import com.sbr.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("create-user")
    public boolean createEntry(@RequestBody User user){
        user.setRoles(Arrays.asList("USER"));
        userService.save(user);
        return true;
    }

    @GetMapping("/find-user-by-id")
    public ResponseEntity<User> getJournalByIdv2(@RequestParam ObjectId userId){
        if(userService.findById(userId).isPresent()){
            return ResponseEntity.ok(userService.findById(userId).get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
