package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.repositories.UserRepository;
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

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/{usernameId}")
    public ResponseEntity<Optional<User>> getWishlistById(@PathVariable("usernameId") UUID userId){

        Optional<User> foundUser = userRepository.findByUserId(userId);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
