package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {
    boolean existsByWishlist_WishlistIdAndProduct_ProductId(Long wishlistId, Long productId);

    void deleteByWishlist_WishlistId(Long wishlistId);

    void deleteByProduct_ProductId(Long productId);

    List<WishlistProduct> findAllByWishlist_WishlistId(Long wishlistId);
}
