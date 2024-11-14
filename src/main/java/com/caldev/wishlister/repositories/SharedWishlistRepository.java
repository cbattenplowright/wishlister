package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.SharedWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedWishlistRepository extends JpaRepository<SharedWishlist, Long> {
}
