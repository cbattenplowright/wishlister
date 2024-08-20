package com.caldev.wishlister.controllers;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Wishlist>> getAllWishlists() {
        List<Wishlist> wishlistList = wishlistService.findAllWishlists();

        if (wishlistList == null) {
            throw new WishlistsNotFoundException("Wishlists not found");
        }

        return new ResponseEntity<>(wishlistList, HttpStatus.OK);
    }

    @GetMapping("/{requestedId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedId")
    public ResponseEntity<Object> getUserWishlists(@PathVariable UUID requestedId, @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<Wishlist> wishlistList = wishlistService.findAllUserWishlists(requestedId);

        if (wishlistList == null) {
            throw new WishlistsNotFoundException("Wishlists not found");
        }

        return new ResponseEntity<>(wishlistList, HttpStatus.OK);
        }
}

