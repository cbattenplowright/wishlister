package com.caldev.wishlister.controllers;

<<<<<<< Updated upstream
=======
import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.UserAccount;
>>>>>>> Stashed changes
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistProductsNotFoundException;
import com.caldev.wishlister.services.WishlistProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes

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

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #newWishlistProductDto.userId")
    public ResponseEntity<Object> createWishlistProduct(@Valid @RequestBody WishlistProductDto newWishlistProductDto,
                                                        @AuthenticationPrincipal UserAccount userAccount){

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean wishlistProductExists = wishlistProductService.existsByWishlistIdAndProductId(newWishlistProductDto.getWishlistId(), newWishlistProductDto.getProductId());

        if (wishlistProductExists) {
            return new ResponseEntity<>("WishlistProduct already exists", HttpStatus.CONFLICT);
        }

        WishlistProduct newWishlistProduct = wishlistProductService.createWishlistProduct(newWishlistProductDto, userAccount);

        return new ResponseEntity<>(newWishlistProductDto, HttpStatus.CREATED);
    }
}
