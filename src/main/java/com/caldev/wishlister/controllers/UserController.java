package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.UserEntity;
import com.caldev.wishlister.models.UserEntityDTO;
import com.caldev.wishlister.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUserDetails authenticatedUser = (SecurityUserDetails) authentication.getPrincipal();
        UserEntity userEntity = authenticatedUser.getUser();

        if (userService.isAuthorizedToViewUserDetails(userEntity.getUserId())) {
            List<UserEntity> userEntities = userService.findAllUsers();
            return ResponseEntity.ok(userEntities);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/{usernameId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("usernameId") UUID userId) {

        /* Pseudo code for this method:
            If user authorised
                If find user
                    return 200 and user
                else
                    return 404 not found
            else 403 forbidden
         */


        if (userService.isAuthorizedToViewUserDetails(userId)) {
            UserEntity foundUserEntity = userService.findUserById(userId);
            if (foundUserEntity != null) {
                return ResponseEntity.ok(foundUserEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntityDTO userEntityDTO) {
        UserEntity createdUserEntity = userService.createUser(userEntityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserEntity);
    }

    @DeleteMapping(value = "/{usernameId}")
    public ResponseEntity<UUID> deleteUser(@PathVariable("usernameId") UUID userId) {

        /* Pseudo code for this method:
            If user authorised
                If find user
                    delete user
                    return 200 and user id
                else
                    return 404 not found
            else 403 forbidden
         */

        if (userService.isAuthorizedToViewUserDetails(userId)) {
            UserEntity foundUserEntity = userService.findUserById(userId);
            if (foundUserEntity != null) {
                UUID deletedUserId = userService.deleteUser(userId);
                return ResponseEntity.ok(deletedUserId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}