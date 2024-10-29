package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.dtos.UserAccountDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAccountDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

//    SHOW User
    @GetMapping("/{requestedUserId}")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getUserById(@PathVariable UUID requestedUserId, @AuthenticationPrincipal UserAccount userAccount){
        System.out.println(userAccount);
        if (userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        UserAccountDto user = userService.getUserById(requestedUserId);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }

//    CREATE User
    @PostMapping("/new")
    public ResponseEntity<UserAccountDto> createUser(@Valid @RequestBody NewUserDto newUserDto){
        UserAccountDto newUserAccount = userService.createUser(newUserDto);
        return new ResponseEntity<>(
                newUserAccount,
                HttpStatus.CREATED);
    }

    //    UPDATE User

    @PatchMapping("/{requestedUserId}")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> updateUser(@PathVariable UUID requestedUserId, @AuthenticationPrincipal UserAccount userAccount, @Valid @RequestBody UserAccountDto userAccountDto){

        if (userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        UserAccountDto updatedUserAccount = userService.updateUser(requestedUserId, userAccountDto);

        if (updatedUserAccount == null){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                updatedUserAccount,
                HttpStatus.OK);
    }

//    DELETE User
    @DeleteMapping("/{requestedId}")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedId")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID requestedId, @AuthenticationPrincipal UserAccount userAccount){
        // Delete products associated with user
        // Delete wishlists associated with user
        // Delete user
        System.out.println(userAccount);
        if (userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        userService.deleteUser(requestedId);
        return new ResponseEntity<>(
                requestedId,
                HttpStatus.OK);
    }


}
