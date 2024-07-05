package com.caldev.wishlister.controllers;

import com.caldev.wishlister.services.UserService;
import com.caldev.wishlister.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID requestedId){
        return ResponseEntity.ok(userService.getUserById(requestedId));
    }
}
