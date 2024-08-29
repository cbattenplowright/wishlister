package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public Wishlist createWishlist(WishlistDto wishlistDto, UserAccount userAccount) {

        Wishlist newWishlist = new Wishlist(
                wishlistDto.getWishlistName(),
                userAccount,
                new ArrayList<>()
        );

        Wishlist savedWishlist = wishlistRepository.save(newWishlist);

        return savedWishlist;
    }

    public boolean existsByWishlistNameAndUserAccount(String wishlistName, UserAccount userAccount) {

        return wishlistRepository.findByWishlistNameAndUserAccount(wishlistName, userAccount);
    }

    public Optional<Wishlist> findWishlistById(Long requestedWishlistId) {
        return wishlistRepository.findById(requestedWishlistId);
    }

    public Wishlist updateWishlist(Long requestedWishlistId, WishlistDto wishlistDto, UserAccount userAccount) {

/*

    PseudoCode for method

    find wishlist by id
        if wishlist not found, throw exception
    else
        if wishlist found, update wishlist
            if userId in wishlistDto is not null, update wishlist userId
            if name in wishlistDto is not null, update wishlist name

*/

        Optional<Wishlist> wishlistToUpdate = findWishlistById(requestedWishlistId);

        if (wishlistToUpdate.isEmpty()) {
            throw new WishlistsNotFoundException("Wishlist not found");
        }

        if (wishlistDto.getUserId() != null && wishlistDto.getUserId() == userAccount.getId()) {
            wishlistToUpdate.get().setUser(userAccount);
        }

        if (wishlistDto.getWishlistName() != null) {
            wishlistToUpdate.get().setWishlistName(wishlistDto.getWishlistName());
        }

        return wishlistRepository.save(wishlistToUpdate.get());
    }

    public void deleteWishlist(Long requestedWishlistId) {
        wishlistRepository.deleteById(requestedWishlistId);
    }
}
