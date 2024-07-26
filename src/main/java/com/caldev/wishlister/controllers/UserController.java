package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    INDEX Users
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAccount>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

//    SHOW User
    @GetMapping("/{requestedId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedId ")
    public ResponseEntity<UserAccount> getUserById(@PathVariable UUID requestedId, @AuthenticationPrincipal UserAccount userAccount){
        System.out.println(userAccount);
        return new ResponseEntity<>(userService.getUserById(requestedId), HttpStatus.OK);
    }

//    CREATE User
    @PostMapping("/new")
    public ResponseEntity<UserAccount> createUser(@RequestBody NewUserDto newUserDto){
        UserAccount newUserAccount = userService.createUser(newUserDto);
        return new ResponseEntity<>(
                newUserAccount,
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
