package com.caldev.wishlister.controllers;

import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.exceptions.ProductNotFoundException;
import com.caldev.wishlister.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    SHOW Products
    @GetMapping("/{requestedUserId}/{requestedProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getProductById(@PathVariable UUID requestedUserId,
                                                 @PathVariable Long requestedProductId,
                                                 @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Product> product = productService.findProductById(requestedProductId);

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        return new ResponseEntity<>(product, HttpStatus.OK);

    }

}
