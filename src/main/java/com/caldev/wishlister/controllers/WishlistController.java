package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.services.WishlistService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

        @GetMapping("/{requestedUserId}/{requestedWishlistId}")
        @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
        public ResponseEntity<Object> getWishlistById(@PathVariable UUID requestedUserId, @PathVariable Long requestedWishlistId, @AuthenticationPrincipal UserAccount userAccount) {

            if (userAccount == null) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            Wishlist wishlist = wishlistService.findWishlistById(requestedWishlistId);

            if (wishlist == null) {
                throw new WishlistsNotFoundException("Wishlist not found");
            }

            return new ResponseEntity<>(wishlist, HttpStatus.OK);

        }

        @PostMapping("/new")
        @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #newWishlistDto.userId")
        public ResponseEntity<Object> createWishlist(@Valid @RequestBody WishlistDto newWishlistDto, @AuthenticationPrincipal UserAccount userAccount) {

            if (userAccount == null) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            boolean wishlistExists = wishlistService.existsByWishlistNameAndUserAccount(newWishlistDto.getWishlistName(), userAccount);

            if (wishlistExists) {
                return new ResponseEntity<>("Wishlist already exists", HttpStatus.CONFLICT);
            }

            Wishlist wishlist = wishlistService.createWishlist(newWishlistDto, userAccount);

            return new ResponseEntity<>(HttpStatus.CREATED);

        }
}


