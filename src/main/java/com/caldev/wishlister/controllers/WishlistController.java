package com.caldev.wishlister.controllers;

import com.caldev.wishlister.models.Wishlist;
import com.caldev.wishlister.services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    private WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
    @GetMapping(value = "/{wishlistId}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long wishlistId){

        Wishlist foundWishlist = wishlistService.findWishlistById(wishlistId);
        if (foundWishlist != null) {
            return ResponseEntity.ok(foundWishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}