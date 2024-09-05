package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    boolean existsByWishlistIdAndProductId(Long wishlistId, Long productId);
}
