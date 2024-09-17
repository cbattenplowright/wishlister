package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.ProductNotFoundException;
import com.caldev.wishlister.repositories.ProductRepository;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final WishlistProductService wishlistProductService;

    private final ProductRepository productRepository;

    private final WishlistProductRepository wishlistProductRepository;

    public ProductService(WishlistProductService wishlistProductService, ProductRepository productRepository, WishlistProductRepository wishlistProductRepository) {
        this.wishlistProductService = wishlistProductService;
        this.productRepository = productRepository;
        this.wishlistProductRepository = wishlistProductRepository;
    }

    public Optional<Product> findProductById(Long requestedProductId) {

        return productRepository.findById(requestedProductId);
    }

    public boolean existsByProductNameAndUserAccount(String productName, UserAccount userAccount) {
        return productRepository.existsByProductNameAndUserAccount(productName, userAccount);
    }

    public Product createProduct(ProductDto newProductDto, UserAccount userAccount) {

/*        if wishlist products list exists
            for every id in list
                if wishlistProductRepository.findById(id)
                    add to newProductDto.wishlistProducts
*/

        List<WishlistProduct> wishlistProducts = new ArrayList<>();

        if (newProductDto.getWishlistProductIds() != null) {
            for (Long wishlistProductId : newProductDto.getWishlistProductIds()){
                WishlistProduct wishlistProduct = wishlistProductRepository.findById(wishlistProductId).get();
                wishlistProducts.add(wishlistProduct);
            }
        }

        Product newProduct = new Product(
                newProductDto.getProductName(),
                newProductDto.getPrice(),
                newProductDto.getUrl(),
                newProductDto.getImageUrl(),
                newProductDto.getDescription(),
                newProductDto.getPrioritySelection(),
                LocalDate.now(),
                wishlistProducts,
                userAccount
        );

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long requestedProductId,
                                 ProductDto updatedProductDto,
                                 UserAccount userAccount) {

        Optional<Product> productToUpdate = findProductById(requestedProductId);

        if (productToUpdate.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }


        if (updatedProductDto.getUserId() != null && userAccount.getId() == updatedProductDto.getUserId()) {
            productToUpdate.get().setUser(userAccount);
        }

        if (updatedProductDto.getProductName() != null) {
            productToUpdate.get().setProductName(updatedProductDto.getProductName());
        }

        if (Objects.nonNull(updatedProductDto.getPrice())) {
            productToUpdate.get().setPrice(updatedProductDto.getPrice());
        }

        if (updatedProductDto.getUrl() != null) {
            productToUpdate.get().setUrl(updatedProductDto.getUrl());
        }

        if (updatedProductDto.getImageUrl() != null) {
            productToUpdate.get().setImageUrl(updatedProductDto.getImageUrl());
        }

        if (updatedProductDto.getPrioritySelection() != null) {
            productToUpdate.get().setPriority(updatedProductDto.getPrioritySelection());
        }

        if (updatedProductDto.getDescription() != null) {
            productToUpdate.get().setDescription(updatedProductDto.getDescription());
        }

        if (updatedProductDto.getDateAdded() != null) {
            productToUpdate.get().setDateAdded(updatedProductDto.getDateAdded());
        }

        if(updatedProductDto.getWishlistProductIds() != null) {
            List<WishlistProduct> wishlistProducts = new ArrayList<>();
            for (Long wishlistProductId : updatedProductDto.getWishlistProductIds()){
                WishlistProduct wishlistProduct = wishlistProductRepository.findById(wishlistProductId).get();
                wishlistProducts.add(wishlistProduct);
            }
            productToUpdate.get().setWishlistProducts(wishlistProducts);
        }

        return productRepository.save(productToUpdate.get());
    }

    public void deleteProduct(Long requestedProductId) {
        wishlistProductService.deleteWishlistProductByProductId(requestedProductId); // cascade delete()
        productRepository.deleteById(requestedProductId);
    }
}
