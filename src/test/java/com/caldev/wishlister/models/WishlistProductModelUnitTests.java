package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNull;

public class WishlistProductModelUnitTests {

    private WishlistProduct wishlistProduct;

    private UserEntity userEntity;

    private Wishlist wishlist;

    private Product product;


    @BeforeEach
    void setUp() {
        // Create a sample wishlist for testing
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        wishlist = new Wishlist("My Wishlist", userEntity);
        product = new Product(userEntity, "testProduct", 15, "https://URL", "https://imageURL", PrioritySelection.DESIRABLE, "description");
        wishlistProduct = new WishlistProduct(wishlist, product);
    }

    @Test
    void testGetWishlistProductId(){
        assertNull(wishlistProduct.getWishlistProductId());
    }
}
