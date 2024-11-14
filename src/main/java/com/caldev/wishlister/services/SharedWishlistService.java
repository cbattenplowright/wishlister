package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.SharedWishlist;
import com.caldev.wishlister.repositories.SharedWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedWishlistService {

    private final SharedWishlistRepository sharedWishlistRepository;

    public SharedWishlistService(SharedWishlistRepository sharedWishlistRepository) {
        this.sharedWishlistRepository = sharedWishlistRepository;
    }

    public List<SharedWishlist> findAllSharedWishlists(){
        return sharedWishlistRepository.findAll();
    }

}
