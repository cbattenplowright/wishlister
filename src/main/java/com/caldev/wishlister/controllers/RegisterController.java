package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.NewUserDto;
import com.caldev.wishlister.dtos.UserAccountDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<UserAccountDto> createUser(@Valid @RequestBody NewUserDto newUserDto){
        UserAccountDto newUserAccount = userService.createUser(newUserDto);
        return new ResponseEntity<>(
                newUserAccount,
                HttpStatus.CREATED);
    }
}
