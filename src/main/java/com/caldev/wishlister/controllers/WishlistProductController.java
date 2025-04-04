package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.UserNotFoundException;
import com.caldev.wishlister.exceptions.WishlistProductsNotFoundException;
import com.caldev.wishlister.services.ProductService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/wishlist-products")
public class WishlistProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistProductService wishlistProductService;


//    INDEX WishlistProducts
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllWishlistProducts(){

        List<WishlistProduct> wishlistProducts = wishlistProductService.getAllWishlistProducts();

        if(wishlistProducts != null){

            List<WishlistProductDto> wishlistProductDtos = wishlistProducts
                    .stream()
                    .map(wishlistProduct -> new WishlistProductDto(
                            wishlistProduct.getWishlistProductId(),
                            wishlistProduct.getWishlist().getWishlistId(),
                            wishlistProduct.getProduct().getProductId(),
                            wishlistProduct.isPurchased())).toList();

            return new ResponseEntity<>(wishlistProductDtos, HttpStatus.OK);
        }

        throw new WishlistProductsNotFoundException("WishlistProducts not found");
    }

//    INDEX User WishlistProducts
    @GetMapping("/{requestedUserId}/{requestedWishlistId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getUserWishlistProducts(@PathVariable UUID requestedUserId,
                                                          @PathVariable UUID requestedWishlistId,
                                                          @AuthenticationPrincipal UserAccount userAccount){
        if(userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<WishlistProduct> wishlistProducts = wishlistProductService.getUserWishlistProducts(requestedWishlistId);

        if(wishlistProducts != null){

            List<WishlistProductDto> wishlistProductDtos = wishlistProducts
                    .stream()
                    .map(wishlistProduct -> new WishlistProductDto(
                            wishlistProduct.getWishlistProductId(),
                            wishlistProduct.getWishlist().getWishlistId(),
                            wishlistProduct.getProduct().getProductId(),
                            wishlistProduct.isPurchased())).toList();

            return new ResponseEntity<>(wishlistProductDtos, HttpStatus.OK);
        }

        throw new WishlistProductsNotFoundException("WishlistProducts not found");
    }

//    SHOW WishlistProduct
    @GetMapping("/{requestedUserId}/{requestedWishlistProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getWishlistProductById(@PathVariable UUID requestedUserId,
                                                         @PathVariable Long requestedWishlistProductId,
                                                         @AuthenticationPrincipal UserAccount userAccount){

//      TODO  When the user is authenticated and the requestedUserId is the same the endpoint can be accessed even if the WishlistProduct does not belong to the user

        if(userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        WishlistProduct wishlistProduct = wishlistProductService.getWishlistProductById(requestedWishlistProductId);

        if(wishlistProduct != null){

            WishlistProductDto wishlistProductDto = new WishlistProductDto(
                    wishlistProduct.getWishlistProductId(),
                    wishlistProduct.getWishlist().getWishlistId(),
                    wishlistProduct.getProduct().getProductId(),
                    wishlistProduct.isPurchased()
            );

            return new ResponseEntity<>(wishlistProductDto, HttpStatus.OK);
        }

        throw new WishlistProductsNotFoundException("WishlistProduct not found");
    }

//    CREATE WishlistProduct

    @PostMapping("/new")
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

        if (newWishlistProduct != null) {

            WishlistProductDto createdWishlistProductDto = new WishlistProductDto(
                    newWishlistProduct.getWishlistProductId(),
                    newWishlistProduct.getWishlist().getWishlistId(),
                    newWishlistProduct.getProduct().getProductId(),
                    newWishlistProduct.isPurchased()
            );

            return new ResponseEntity<>(createdWishlistProductDto, HttpStatus.CREATED);
        }

        throw new WishlistProductsNotFoundException("WishlistProduct not created");
    }

//    UPDATE WishlistProduct
    @PutMapping("/{requestedUserId}/{requestedWishlistProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> updateWishlistProduct(@PathVariable UUID requestedUserId,
                                                        @PathVariable Long requestedWishlistProductId,
                                                        @Valid @RequestBody WishlistProductDto wishlistProductDtoToUpdate,
                                                        @AuthenticationPrincipal UserAccount userAccount){

        if(userAccount == null){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean wishlistProductExists = wishlistProductService.existsByWishlistIdAndProductId(wishlistProductDtoToUpdate.getWishlistId(), wishlistProductDtoToUpdate.getProductId());

        if(wishlistProductExists){
            WishlistProduct updatedWishlistProduct = wishlistProductService.updateWishlistProduct(requestedWishlistProductId, wishlistProductDtoToUpdate, userAccount);

            WishlistProductDto updatedWishlistProductDto = new WishlistProductDto(
                    updatedWishlistProduct.getWishlistProductId(),
                    updatedWishlistProduct.getWishlist().getWishlistId(),
                    updatedWishlistProduct.getProduct().getProductId(),
                    updatedWishlistProduct.isPurchased()
            );

            return new ResponseEntity<>(updatedWishlistProductDto, HttpStatus.OK);
        }
        else {
            throw new WishlistProductsNotFoundException("WishlistProduct not found");
        }
    }
}
