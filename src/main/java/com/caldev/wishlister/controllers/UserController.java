package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.UserDTO;
import com.caldev.wishlister.repositories.UserRepository;
import com.caldev.wishlister.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUserDetails authenticatedUser = (SecurityUserDetails) authentication.getPrincipal();
        User user = authenticatedUser.getUser();

        if (userService.isAuthorizedToViewUserDetails(user.getUserId())) {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/{usernameId}")
    public ResponseEntity<User> getUserById(@PathVariable("usernameId") UUID userId) {

        /* Pseudo code for this method:
            If user authorised
                If find user
                    return 200 and user
                else
                    return 404 not found
            else 403 forbidden
         */


        if (userService.isAuthorizedToViewUserDetails(userId)) {
            User foundUser = userService.findUserById(userId);
            if (foundUser != null) {
                return ResponseEntity.ok(foundUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}