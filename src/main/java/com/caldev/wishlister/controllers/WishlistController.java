package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.PendingShareDto;
import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.PendingShare;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.services.EmailService;
import com.caldev.wishlister.services.UserService;
import com.caldev.wishlister.services.WishlistProductService;
import com.caldev.wishlister.services.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@RestController
@RequestMapping(value = "/api/wishlists")
public class WishlistController {

    @Autowired
    private UserService userService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistProductService wishlistProductService;

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
                            wishlist.getWishlistId(),
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
    public ResponseEntity<Object> getUserWishlists(
            @PathVariable UUID requestedUserId,
            @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<Wishlist> wishlistList = wishlistService.findAllUserWishlists(requestedUserId);

        if (wishlistList != null) {

            List<WishlistDto> wishlistDtoList = wishlistList
                    .stream()
                    .map(wishlist -> new WishlistDto(
                            wishlist.getWishlistId(),
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
    public ResponseEntity<Object> getWishlistById(
            @PathVariable UUID requestedUserId,
            @PathVariable Long requestedWishlistId,
            @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

//        Optional<Wishlist> wishlist = wishlistService.findWishlistById(requestedWishlistId);
//        System.out.println(wishlist);
//
//        if (wishlist.isPresent()) {
//            List<WishlistProduct> wishlistProductsList = wishlistProductService.getAllWishlistProductsByWishlistId(requestedWishlistId);
//
//
//            WishlistDto wishlistDto = new WishlistDto(
//                    wishlist.get().getWishlistId(),
//                    wishlist.get().getUserAccount().getId(),
//                    wishlist.get().getWishlistName()
//            );

//            return new ResponseEntity<>(wishlistDto, HttpStatus.OK);

        return wishlistService.findWishlistByIdWithProducts(requestedWishlistId)
                .map(wishlistDto -> {
                    List<ProductDto> products = wishlistDto.getProducts().stream()
                            .map(productDto -> new ProductDto(
                                    productDto.getProductId(),
                                    productDto.getProductName(),
                                    productDto.getPrice(),
                                    productDto.isPurchased(),
                                    productDto.getWishlistProductId()
                            )).toList();

                    WishlistDto response = new WishlistDto(
                            wishlistDto.getWishlistId(),
                            wishlistDto.getUserId(),
                            wishlistDto.getWishlistName(),
                            products
                    );

                    return new ResponseEntity<Object>(response, HttpStatus.OK);
                }).orElseThrow(()->
                        new WishlistsNotFoundException("Wishlist not found"));
//                        new ResponseEntity<Object>(wishlistDto, HttpStatus.OK))
//                .orElseThrow(() -> new WishlistsNotFoundException("Wishlist not found"));
    }


    // CREATE Wishlist
    @PostMapping("/new")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #newWishlistDto.userId")
    public ResponseEntity<Object> createWishlist(
            @Valid @RequestBody WishlistDto newWishlistDto,
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
                    newWishlist.getWishlistId(),
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
                    updatedWishlist.getWishlistId(),
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
    public ResponseEntity<Object> deleteWishlist(
            @PathVariable UUID requestedUserId,
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

//    Share Wishlist endpoint

    @PostMapping("/{requestedUserId}/{requestedWishlistId}/share")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> shareWishlist(
            @PathVariable UUID requestedUserId,
            @PathVariable Long requestedWishlistId,
            @RequestParam String email,
            @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Wishlist> existingWishlist = wishlistService.findWishlistById(requestedWishlistId);

        if (existingWishlist.isPresent()) {
//            emailService.sendEmail();
            wishlistService.shareWishlist(existingWishlist.get(), userAccount, email);

            return new ResponseEntity<>("Wishlist shared!", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    Confirm share wishlist endpoint

    @PostMapping("/confirm-share")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> acceptShare(
            @RequestParam String shareToken,
            @AuthenticationPrincipal UserAccount userAccount){

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean shareTokenExists = wishlistService.verifyShareToken(shareToken);

        boolean userAccountMatchesWithShareToken = wishlistService.userAccountMatchesWithRecipientEmail(shareToken, userAccount.getEmail());

        if (shareTokenExists && userAccountMatchesWithShareToken) {
            wishlistService.confirmShare(shareToken, userAccount);
            return new ResponseEntity<>("Share accepted!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Share token not valid", HttpStatus.NOT_FOUND);
        }

    }

//    INDEX User Pending Shares endpoint

    @GetMapping("/pending-shares/{requestedUserId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getPendingShares(
            @PathVariable UUID requestedUserId,
            @AuthenticationPrincipal UserAccount userAccount) {
        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        List<PendingShare> pendingShares = wishlistService.findPendingSharesByRecipientEmail(userAccount.getEmail());
        if (pendingShares.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        List <PendingShareDto> pendingShareDtos = pendingShares.stream()
                .map(pendingShare -> new PendingShareDto(
                        pendingShare.getPendingShareId(),
                        pendingShare.getToken(),
                        pendingShare.getWishlistId(),
                        pendingShare.getSenderUserId(),
                        pendingShare.getRecipientEmail()
                ))
                .toList();

        return new ResponseEntity<>(pendingShareDtos, HttpStatus.OK);
    }

}


