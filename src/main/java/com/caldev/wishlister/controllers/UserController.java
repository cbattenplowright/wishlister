package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.UserRepository;
import com.caldev.wishlister.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{usernameId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("usernameId") UUID userId){

        Optional<User> foundUser = userService.findUserById(userId);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
