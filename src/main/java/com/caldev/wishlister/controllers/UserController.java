package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.Wishlist;
import com.caldev.wishlister.repositories.UserRepository;
import com.caldev.wishlister.services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<Optional<User>> getWishlistById(@PathVariable("username") String username){

        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
