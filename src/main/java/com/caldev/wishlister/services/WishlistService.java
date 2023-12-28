package com.caldev.wishlister.services;

import com.caldev.wishlister.models.Wishlist;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Optional<Wishlist> findWishlistById(Long wishlistId) {

        return wishlistRepository.findById(wishlistId);
    }

}
