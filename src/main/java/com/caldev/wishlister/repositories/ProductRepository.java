package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsByProductIdAndUserAccount(Long productId, UserAccount userAccount);

    boolean existsByProductNameAndUserAccount(String productName, UserAccount userAccount);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.productId = :productId")
    void deleteByProductId(Long productId);
}
