package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {
    boolean existsByWishlist_WishlistIdAndProduct_ProductId(Long wishlistId, Long productId);

    List<WishlistProduct> findAllByWishlist_WishlistId(Long wishlistId);

    @Modifying
    @Query("delete from WishlistProduct wp where wp.wishlist.wishlistId = :wishlistId")
    void deleteByWishlistId(@Param("wishlistId")Long wishlistId);
}
