package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {
}
