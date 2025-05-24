package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserAccount_Id(UUID userId);
    boolean existsByWishlistIdAndUserAccount(Long wishlistId, UserAccount userAccount);
    boolean existsByWishlistNameAndUserAccount(String wishlistName, UserAccount userAccount);

    @Query("SELECT w.wishlistName FROM Wishlist w WHERE w.wishlistId = :requestedWishlistId")
    String findWishlistNameById(@Param("requestedWishlistId") Long requestedWishlistId);

    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.wishlistId = :wishlistId")
    void deleteByWishlistId(Long wishlistId);

    @Query("SELECT w FROM Wishlist w " +
            "LEFT JOIN FETCH w.wishlistProducts wp " +
            "LEFT JOIN FETCH wp.product p " +
            "WHERE w.wishlistId = :wishlistId")
    Optional<Wishlist> findByIdWithProducts(@Param("wishlistId") Long wishlistId);

}
