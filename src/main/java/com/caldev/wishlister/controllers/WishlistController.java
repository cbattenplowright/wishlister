package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.services.EmailService;
import com.caldev.wishlister.services.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private EmailService emailService;

    // INDEX Wishlists
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WishlistDto>> getAllWishlists() {
        List<Wishlist> wishlistList = wishlistService.findAllWishlists();

        if (wishlistList != null) {

            List<WishlistDto> wishlistDtoList = wishlistList
                    .stream()
                    .map(wishlist -> new WishlistDto(
                            wishlist.getUserAccount().getId(),
                            wishlist.getWishlistName()
                    )).toList();

            return new ResponseEntity<>(wishlistDtoList, HttpStatus.OK);
        }

        throw new WishlistsNotFoundException("Wishlists not found");
    }

    // INDEX User Wishlists
    @GetMapping("/{requestedUserId}")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getUserWishlists(@PathVariable UUID requestedUserId,
                                                   @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<Wishlist> wishlistList = wishlistService.findAllUserWishlists(requestedUserId);

        if (wishlistList != null) {

            List<WishlistDto> wishlistDtoList = wishlistList
                    .stream()
                    .map(wishlist -> new WishlistDto(
                            wishlist.getUserAccount().getId(),
                            wishlist.getWishlistName()
                    )).toList();

            return new ResponseEntity<>(wishlistDtoList, HttpStatus.OK);
        }

        throw new WishlistsNotFoundException("Wishlists not found");
    }

    // SHOW Wishlist
    @GetMapping("/{requestedUserId}/{requestedWishlistId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getWishlistById(@PathVariable UUID requestedUserId,
                                                  @PathVariable Long requestedWishlistId,
                                                  @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Wishlist> wishlist = wishlistService.findWishlistById(requestedWishlistId);

        if (wishlist.isPresent()) {

            WishlistDto wishlistDto = new WishlistDto(
                    wishlist.get().getUserAccount().getId(),
                    wishlist.get().getWishlistName()
            );

            return new ResponseEntity<>(wishlistDto, HttpStatus.OK);
        }

        throw new WishlistsNotFoundException("Wishlist not found");

    }


    // CREATE Wishlist
    @PostMapping("/new")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #newWishlistDto.userId")
    public ResponseEntity<Object> createWishlist(@Valid @RequestBody WishlistDto newWishlistDto,
                                                 @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean wishlistExists = wishlistService.existsByWishlistNameAndUserAccount(newWishlistDto.getWishlistName(), userAccount);

        if (wishlistExists) {
            return new ResponseEntity<>("Wishlist already exists", HttpStatus.CONFLICT);
        }

        Wishlist newWishlist = wishlistService.createWishlist(newWishlistDto, userAccount);

        if (newWishlist != null) {

            WishlistDto createdWishlistDto = new WishlistDto(
                    newWishlist.getUserAccount().getId(),
                    newWishlist.getWishlistName()
            );

            return new ResponseEntity<>(createdWishlistDto, HttpStatus.CREATED);
        }

        throw new WishlistsNotFoundException("Wishlist not found");
    }

//        UPDATE Wishlist

    @PatchMapping("/{requestedUserId}/{requestedWishlistId}")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> updateWishlist(
            @PathVariable UUID requestedUserId,
            @PathVariable int requestedWishlistId,
            @Valid @RequestBody WishlistDto wishlistDto,
            @AuthenticationPrincipal UserAccount userAccount) {

    /*
    check if user is authorized
        if not, then return unauthorized

    find wishlist
        if not found, then return not found

    update wishlist
    */

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Wishlist updatedWishlist = wishlistService.updateWishlist((long) requestedWishlistId, wishlistDto, userAccount);

        if (updatedWishlist != null) {

            WishlistDto wishlistDtoToReturn = new WishlistDto(
                    updatedWishlist.getUserAccount().getId(),
                    updatedWishlist.getWishlistName()
            );

            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        }

        return new ResponseEntity<>("Wishlist not found", HttpStatus.NOT_FOUND);

    }

//  DELETE Wishlist
    @DeleteMapping("/{requestedUserId}/{requestedWishlistId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> deleteWishlist(@PathVariable UUID requestedUserId,
                                                 @PathVariable Long requestedWishlistId,
                                                 @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Wishlist> existingWishlist = wishlistService.findWishlistById(requestedWishlistId);

        if (existingWishlist.isPresent()) {
            System.out.println(existingWishlist.get());
            wishlistService.deleteWishlist(existingWishlist.get().getId());
            return new ResponseEntity<>(requestedWishlistId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{requestedUserId}/{requestedWishlistId}/share")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> shareWishlist(@PathVariable UUID requestedUserId,
                                                @PathVariable Long requestedWishlistId,
                                                @RequestParam String email,
                                                @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Wishlist> existingWishlist = wishlistService.findWishlistById(requestedWishlistId);

        if (existingWishlist.isPresent()) {
            emailService.sendEmail();
//            wishlistService.shareWishlist(existingWishlist.get(), userAccount, email);
            return new ResponseEntity<>(existingWishlist.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


