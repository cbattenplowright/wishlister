package com.caldev.wishlister.services;

import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.entities.WishlistProduct;
import com.caldev.wishlister.exceptions.WishlistProductNotCreatedException;
import com.caldev.wishlister.exceptions.WishlistProductsNotFoundException;
import com.caldev.wishlister.exceptions.WishlistProductNotOwnedByUserAccountException;
import com.caldev.wishlister.repositories.WishlistProductRepository;
import jakarta.transaction.Transactional;
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

    public List<WishlistProduct> getAllWishlistProductsByWishlistId(Long wishlistId) {
        return wishlistProductRepository.findAllByWishlist_WishlistId(wishlistId);
    }

    public boolean existsByWishlistIdAndProductId(Long wishlistId, Long productId) {
        return wishlistProductRepository.existsByWishlist_WishlistIdAndProduct_ProductId(wishlistId, productId);
    }

    public WishlistProduct createWishlistProduct(WishlistProductDto newWishlistProductDto, UserAccount userAccount) throws WishlistProductNotOwnedByUserAccountException, WishlistProductNotCreatedException {

        boolean userOwnsWishlist = wishlistService.userAccountOwnsWishlist(newWishlistProductDto.getWishlistId(), userAccount);
        boolean userOwnsProduct = productService.userAccountOwnsProduct(newWishlistProductDto.getProductId(), userAccount);

        if (!userOwnsWishlist || !userOwnsProduct) {
            throw new WishlistProductNotOwnedByUserAccountException("User does not own wishlist or product");
        }

       Optional<Wishlist> wishlist = wishlistService.findWishlistById(newWishlistProductDto.getWishlistId());
       ProductDto product = productService.findProductById(newWishlistProductDto.getProductId());

       if (wishlist.isPresent() || product.isPresent()) {
           WishlistProduct newWishlistProduct = new WishlistProduct(
                   wishlist.get(),
                   product.get(),
                   newWishlistProductDto.isPurchased()
           );

           return wishlistProductRepository.save(newWishlistProduct);
       }

       throw new WishlistProductNotCreatedException("WishlistProduct not created");

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

    @Transactional
    public void deleteWishlistProduct(Long wishlistProductId) {
        wishlistProductRepository.deleteById(wishlistProductId);
    }

    @Transactional
    public void deleteByWishlistId(Long wishlistId) {
        wishlistProductRepository.deleteByWishlistId(wishlistId);
    }

    @Transactional
    public void deleteByProductId(Long productId) {
        wishlistProductRepository.deleteByProductId(productId);
    }
}
