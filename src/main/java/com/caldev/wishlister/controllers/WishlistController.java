package com.caldev.wishlister.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @GetMapping("/{wishlistId}")
    public ResponseEntity<String> findWishlistById(@PathVariable Long wishlistId){
        return ResponseEntity.notFound().build();
    }
}