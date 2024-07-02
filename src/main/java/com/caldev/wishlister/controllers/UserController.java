package com.caldev.wishlister.controllers;

import com.caldev.wishlister.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(UserService.getUsers());
    }
}
