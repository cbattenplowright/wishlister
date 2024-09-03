package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistProductService {

    private final WishlistProductRepository wishlistProductRepository;

    public WishlistProductService(WishlistProductRepository wishlistProductRepository) {
        this.wishlistProductRepository = wishlistProductRepository;
    }

    public List<WishlistProduct> getAllWishlistProducts() {
        return wishlistProductRepository.findAll();
    }
}
