package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistProductsNotFoundException;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistProductService {

    private final ProductService productService;
    private final WishlistService wishlistService;
    private final WishlistProductRepository wishlistProductRepository;

    public WishlistProductService(@Lazy ProductService productService, @Lazy WishlistService wishlistService, WishlistProductRepository wishlistProductRepository) {
        this.productService = productService;
        this.wishlistService = wishlistService;
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

    public WishlistProduct updateWishlistProduct(Long requestedWishlistProductId, WishlistProductDto updatedWishlistProductDto, UserAccount userAccount) {

        Optional<WishlistProduct> wishlistProductToUpdate = wishlistProductRepository.findById(requestedWishlistProductId);
        Optional<Wishlist> wishlist = wishlistService.findWishlistById(updatedWishlistProductDto.getWishlistId());
        Optional<Product> product = productService.findProductById(updatedWishlistProductDto
            .getProductId());

//        Do I just want to change the isPurchased value for the wishlistProductToUpdate
        if (wishlistProductToUpdate.isPresent()) {
            wishlistProductToUpdate.get().setIsPurchased(updatedWishlistProductDto.isPurchased());
            return wishlistProductRepository.save(wishlistProductToUpdate.get());
        }

        throw new WishlistProductsNotFoundException("WishlistProduct not found");
    }

    public void deleteWishlistProduct(Long wishlistProductId) {
        wishlistProductRepository.deleteById(wishlistProductId);
    }

    public void deleteWishlistProductByWishlistId(Long wishlistId) {
        wishlistProductRepository.deleteByWishlist_WishlistId(wishlistId);
    }

    public void deleteWishlistProductByProductId(Long productId) {
        wishlistProductRepository.deleteByProduct_ProductId(productId);
    }
}
