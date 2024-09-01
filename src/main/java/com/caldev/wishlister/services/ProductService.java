package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.repositories.ProductRepository;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final WishlistProductRepository wishlistProductRepository;

    public ProductService(ProductRepository productRepository, WishlistProductRepository wishlistProductRepository) {
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

    public void deleteProduct(Long requestedProductId) {
        productRepository.deleteById(requestedProductId);
    }
}
