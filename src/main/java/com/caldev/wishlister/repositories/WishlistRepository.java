package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserAccount_Id(UUID userId);
    boolean existsByWishlistIdAndUserAccount(Long wishlistId, UserAccount userAccount);
    boolean existsByWishlistNameAndUserAccount(String wishlistName, UserAccount userAccount);
}
