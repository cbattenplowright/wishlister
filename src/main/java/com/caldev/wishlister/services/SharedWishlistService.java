package com.caldev.wishlister.services;

import com.caldev.wishlister.entities.SharedWishlist;
import com.caldev.wishlister.repositories.SharedWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SharedWishlistService {

    private final SharedWishlistRepository sharedWishlistRepository;

    public SharedWishlistService(SharedWishlistRepository sharedWishlistRepository) {
        this.sharedWishlistRepository = sharedWishlistRepository;
    }

    public List<SharedWishlist> findAllSharedWishlists(){
        return sharedWishlistRepository.findAll();
    }

    public List<SharedWishlist> findAllSharedWishlistsByUser(UUID requestedUserId){
        return sharedWishlistRepository.findAllBySharedUserAccount_Id(requestedUserId);
    }

    public Optional<SharedWishlist> findSharedWishlistById(Long sharedWishlistId){
        return sharedWishlistRepository.findById(sharedWishlistId);
    }

}
