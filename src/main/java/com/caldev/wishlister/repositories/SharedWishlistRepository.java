package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.SharedWishlist;
import com.caldev.wishlister.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SharedWishlistRepository extends JpaRepository<SharedWishlist, Long> {

    List<SharedWishlist> findAllBySharedUserAccount_Id(UUID userId);
}
