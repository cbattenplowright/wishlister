package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.SharedWishlistDto;
import com.caldev.wishlister.entities.SharedWishlist;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.services.SharedWishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/shared-wishlists")
public class SharedWishlistController {

    private final SharedWishlistService sharedWishlistService;

    public SharedWishlistController(SharedWishlistService sharedWishlistService) {
        this.sharedWishlistService = sharedWishlistService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getSharedWishlists() {
        List<SharedWishlist> sharedWishlistList = sharedWishlistService.findAllSharedWishlists();

        if (sharedWishlistList != null) {

            List<SharedWishlistDto> sharedWishlistDtoList = sharedWishlistList
                    .stream()
                    .map(sharedWishlist -> new SharedWishlistDto(
                            sharedWishlist.getSharedWishlistId(),
                            sharedWishlist.getSharedUser().getUserAccountId(),
                            sharedWishlist.getSharedWishlist().getWishlistId())
                    ).toList();

            return new ResponseEntity<>(sharedWishlistDtoList, HttpStatus.OK);
        }

        return new ResponseEntity<>("No wishlists not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{requestedUserId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getSharedWishlistsByUser(
            @PathVariable UUID requestedUserId,
            @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<SharedWishlist> sharedWishlistList = sharedWishlistService.findAllSharedWishlistsByUser();

        if (sharedWishlistList != null) {

            List<SharedWishlistDto> sharedWishlistDtoList = sharedWishlistList
                    .stream()
                    .map(sharedWishlist -> new SharedWishlistDto(
                            sharedWishlist.getSharedWishlistId(),
                            sharedWishlist.getSharedUser().getUserAccountId(),
                            sharedWishlist.getSharedWishlist().getWishlistId())
                    ).toList();

            return new ResponseEntity<>(sharedWishlistDtoList, HttpStatus.OK);
        }

        throw new SharedWishlistsNotFoundException("Shared wishlists not found");
    }
}
