package com.caldev.wishlister.controllers;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.UserNotFoundException;
import com.caldev.wishlister.exceptions.WishlistProductsNotFoundException;
import com.caldev.wishlister.services.WishlistProductService;
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
@RequestMapping(value = "/api/wishlist-products")
public class WishlistProductController {

    @Autowired
    private WishlistProductService wishlistProductService;


//    INDEX WishlistProducts
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllWishlistProducts(){

        List<WishlistProduct> wishlistProducts = wishlistProductService.getAllWishlistProducts();

        if(wishlistProducts == null){
            throw new WishlistProductsNotFoundException("WishlistProducts not found");
        }

        return new ResponseEntity<>(wishlistProducts, HttpStatus.OK);
    }

//    SHOW WishlistProduct
    @GetMapping("/{requestedUserId}/{requestedWishlistProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getWishlistProductById(@PathVariable UUID requestedUserId,
                                                         @PathVariable Long requestedWishlistProductId,
                                                         @AuthenticationPrincipal UserAccount userAccount){

        if(userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        WishlistProduct wishlistProduct = wishlistProductService.getWishlistProductById(requestedWishlistProductId);

        if(wishlistProduct != null){
            return new ResponseEntity<>(wishlistProduct, HttpStatus.OK);
        }

        throw new WishlistProductsNotFoundException("WishlistProduct not found");
    }
}
