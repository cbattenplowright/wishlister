package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.dtos.UserDto;
import com.caldev.wishlister.services.UserService;
import com.caldev.wishlister.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    INDEX Users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

//    SHOW User
    @GetMapping("/{requestedId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID requestedId){
        return ResponseEntity.ok(userService.getUserById(requestedId));
    }

//    CREATE User
    @PostMapping("/new")
    public ResponseEntity<User> createUser(@RequestBody NewUserDto newUserDto){
        User newUser = userService.createUser(newUserDto);
        return ResponseEntity.ok(newUser);
    }

//    DELETE User
    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID requestedId){
        userService.deleteUser(requestedId);
        return ResponseEntity.ok().build();
    }
}
