package com.caldev.wishlister.controllers;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.ProductNotFoundException;
import com.caldev.wishlister.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    SHOW Product
    @GetMapping("/{requestedUserId}/{requestedProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> getProductById(@PathVariable UUID requestedUserId,
                                                 @PathVariable Long requestedProductId,
                                                 @AuthenticationPrincipal UserAccount userAccount) {

        if (userAccount == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Product> product = productService.findProductById(requestedProductId);

        if (product.isPresent()) {

            ProductDto productDto = new ProductDto(
                    product.get().getProductId(),
                    product.get().getProductName(),
                    product.get().getUserAccount().getId(),
                    product.get().getPrice(),
                    product.get().getUrl(),
                    product.get().getImageUrl(),
                    product.get().getPriority(),
                    product.get().getDescription(),
                    product.get().getDateAdded(),
                    new ArrayList<>(product.get().getWishlistProducts().stream().map(WishlistProduct::getWishlistProductId).toList())
            );

            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }

        throw new ProductNotFoundException("Product not found");
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

        if (newProduct != null) {
            ProductDto productDto = new ProductDto(
                    newProduct.getProductId(),
                    newProduct.getProductName(),
                    newProduct.getUserAccount().getId(),
                    newProduct.getPrice(),
                    newProduct.getUrl(),
                    newProduct.getImageUrl(),
                    newProduct.getPriority(),
                    newProduct.getDescription(),
                    newProduct.getDateAdded(),
                    new ArrayList<>(newProduct.getWishlistProducts().stream().map(WishlistProduct::getWishlistProductId).toList())
            );

            return new ResponseEntity<>(productDto, HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Product not created", HttpStatus.BAD_REQUEST);
    }

//    UPDATE Product

    @PatchMapping("/{requestedUserId}/{requestedProductId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER') && #userAccount.id == #requestedUserId")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID requestedUserId,
                                                @PathVariable Long requestedProductId,
                                                @Valid @RequestBody ProductDto productDtoToUpdate,
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

        Product updatedProduct = productService.updateProduct(requestedProductId, productDtoToUpdate, userAccount);

        if (updatedProduct != null) {

            ProductDto updatedProductDto = new ProductDto(
                    updatedProduct.getProductId(),
                    updatedProduct.getProductName(),
                    updatedProduct.getUserAccount().getId(),
                    updatedProduct.getPrice(),
                    updatedProduct.getUrl(),
                    updatedProduct.getImageUrl(),
                    updatedProduct.getPriority(),
                    updatedProduct.getDescription(),
                    updatedProduct.getDateAdded(),
                    new ArrayList<>(updatedProduct.getWishlistProducts().stream().map(WishlistProduct::getWishlistProductId).toList())
            );

            return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
        }

        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
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
