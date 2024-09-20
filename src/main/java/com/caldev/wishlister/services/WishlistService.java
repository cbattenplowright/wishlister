package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistsNotFoundException;
import com.caldev.wishlister.repositories.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService {

    private final UserService userService;

    private final WishlistProductService wishlistProductService;

    private final WishlistRepository wishlistRepository;


    public WishlistService(UserService userService,WishlistProductService wishlistProductService, WishlistRepository wishlistRepository) {
        this.wishlistProductService = wishlistProductService;
        this.userService = userService;
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> findAllWishlists() {
        return wishlistRepository.findAll();
    }

    public List<Wishlist> findAllUserWishlists(UUID requestedId) {
        List<Wishlist> wishlistList = wishlistRepository.findAllByUserAccount_Id(requestedId);
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

        return wishlistRepository.existsByWishlistNameAndUserAccount(wishlistName, userAccount);
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
            wishlistToUpdate.get().setUserAccount(userAccount);
        }

        if (wishlistDto.getWishlistName() != null) {
            wishlistToUpdate.get().setWishlistName(wishlistDto.getWishlistName());
        }

        return wishlistRepository.save(wishlistToUpdate.get());
    }

    @Transactional
    public void deleteWishlist(Long requestedWishlistId) {

        System.out.println("Deleting wishlist with ID: " + requestedWishlistId);

        List<WishlistProduct> wishlistProducts = wishlistProductService.getAllWishlistProductsByWishlistId(requestedWishlistId);
        System.out.println("Found {%s} wishlist products for wishlist with ID: {%s}" + wishlistProducts.size() + requestedWishlistId);

        for (WishlistProduct wishlistProduct : wishlistProducts) {
            System.out.println("Deleting wishlist product with ID: {}" + wishlistProduct.getWishlist().getId());
            wishlistProductService.deleteWishlistProductByWishlistId(wishlistProduct.getWishlist().getId());
        }

        System.out.println("Deleting wishlist with ID: {%s}" + requestedWishlistId);
        wishlistRepository.deleteById(requestedWishlistId);
//        List<WishlistProduct> wishlistProducts = wishlistProductService.getAllWishlistProductsByWishlistId(requestedWishlistId);
//
//        for (WishlistProduct wishlistProduct : wishlistProducts) {
//            wishlistProductService.deleteWishlistProductByWishlistId(wishlistProduct.getWishlist().getId());
//        }
//
////        wishlistProductService.deleteWishlistProductByWishlistId(requestedWishlistId);
//
//        wishlistRepository.deleteById(requestedWishlistId);
    }

    public boolean userAccountOwnsWishlist(Long wishlistId, UserAccount userAccount) {
        return wishlistRepository.existsByWishlistIdAndUserAccount(wishlistId, userAccount);
    }
}
