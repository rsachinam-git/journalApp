package com.sbr.journalapp.controller;

import com.sbr.journalapp.entity.User;
import com.sbr.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public List<User> getAll(){
        return (userService.findAll());
    }

    @PostMapping("create-admin")
    public boolean createAdmin(@RequestBody User user){
        user.setRoles(Arrays.asList("ADMIN"));
        userService.save(user);
        return true;
    }

}
