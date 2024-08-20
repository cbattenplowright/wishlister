package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    private final UserService userService;

    public WishlistService(WishlistRepository wishlistRepository, UserService userService) {
        this.wishlistRepository = wishlistRepository;
        this.userService = userService;
    }

    public List<Wishlist> findAllWishlists() {
        return wishlistRepository.findAll();
    }
}
