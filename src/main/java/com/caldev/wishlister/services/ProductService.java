package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductById(Long requestedProductId) {

        return productRepository.findById(requestedProductId);
    }

    public boolean existsByProductNameAndUserAccount(String productName, UserAccount userAccount) {
        return productRepository.existsByProductNameAndUserAccount(productName, userAccount);
    }
}
