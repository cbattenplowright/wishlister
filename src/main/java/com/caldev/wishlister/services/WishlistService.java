package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<Wishlist> findAllUserWishlists(UUID requestedId) {
        List<Wishlist> wishlistList = wishlistRepository.findAllByUserId(requestedId);
        return wishlistList;
    }

    public Wishlist findWishlistById(Long requestedId) {
        Wishlist wishlist = wishlistRepository.findById(requestedId).orElse(null);

        return wishlist;
    }

    public Wishlist createWishlist(WishlistDto wishlistDto, UserAccount userAccount) {

        Wishlist newWishlist = new Wishlist(
                wishlistDto.getWishlistName(),
                userAccount,
                new ArrayList<>()
        );

        Wishlist savedWishlist = wishlistRepository.save(newWishlist);

        return savedWishlist;
    }
}
