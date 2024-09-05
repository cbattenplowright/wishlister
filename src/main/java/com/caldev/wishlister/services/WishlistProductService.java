package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.UserAccount;
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

    public WishlistProduct getWishlistProductById(Long wishlistProductId) {
        return wishlistProductRepository.findById(wishlistProductId).get();
    }

    public boolean existsByWishlistIdAndProductId(Long wishlistId, Long productId) {
        return wishlistProductRepository.existsByWishlist_WishlistIdAndProduct_ProductId(wishlistId, productId);
    }

    public WishlistProduct createWishlistProduct(WishlistProductDto newWishlistProductDto, UserAccount testUserAccount) {
        return null;
    }
}
