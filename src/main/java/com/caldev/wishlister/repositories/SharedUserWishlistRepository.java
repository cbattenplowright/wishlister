package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.SharedUserWishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedUserWishlistRepository extends CrudRepository<SharedUserWishlist, Long> {
}
