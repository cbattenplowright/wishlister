package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.dtos.UserDto;
import com.caldev.wishlister.services.UserService;
import com.caldev.wishlister.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

//    SHOW User
    @GetMapping("/{requestedId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID requestedId){
        return new ResponseEntity<>(userService.getUserById(requestedId), HttpStatus.OK);
    }

//    CREATE User
    @PostMapping("/new")
    public ResponseEntity<User> createUser(@RequestBody NewUserDto newUserDto){
        User newUser = userService.createUser(newUserDto);
        return new ResponseEntity<>(
                newUser,
                HttpStatus.CREATED);
    }

//    DELETE User
    @DeleteMapping("/{requestedId}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID requestedId){
        // Delete products associated with user
        // Delete wishlists associated with user
        // Delete user
        userService.deleteUser(requestedId);
        return new ResponseEntity<>(
                requestedId,
                HttpStatus.OK);
    }
}