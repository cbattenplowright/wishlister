package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.ProductNotFoundException;
import com.caldev.wishlister.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

//  CREATE Product

    @PostMapping("/new")
    @PostAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #newProductDto.userId")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDto newProductDto,
                                                @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean productExists = productService.existsByProductNameAndUserAccount(newProductDto.getProductName(), userAccount);

        if (productExists) {
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        }

        Product newProduct = productService.createProduct(newProductDto, userAccount);

        return new ResponseEntity<>(newProductDto, HttpStatus.CREATED);

    }

//    UPDATE Product

    @PatchMapping("/{requestedUserId}/{requestedProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID requestedUserId,
                                                @PathVariable Long requestedProductId,
                                                @Valid @RequestBody ProductDto updatedProductDto,
                                                @AuthenticationPrincipal UserAccount userAccount) {
/*        check if user is authenticated
            if not, return unauthenticated
          find product
            if not found, return not found
          update product
 */

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Product updatedProduct = productService.updateProduct(requestedProductId, updatedProductDto, userAccount);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

//    DELETE Product

    @DeleteMapping("/{requestedUserId}/{requestedProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID requestedUserId,
                                                @PathVariable Long requestedProductId,
                                                @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Product> existingProduct = productService.findProductById(requestedProductId);

        if (existingProduct.isPresent()) {
            productService.deleteProduct(requestedProductId);
            return new ResponseEntity<>(requestedProductId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
