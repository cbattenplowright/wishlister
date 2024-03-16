package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class WishlistModelUnitTests {

    private Wishlist wishlist;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        // Create a sample wishlist for testing
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        wishlist = new Wishlist("My Wishlist", userEntity);
    }

    @Test
    void getWishlistId() {
        // Test getters
        assertNull(wishlist.getWishlistId());
    }

    @Test
    void getWishlistName() {
        assertEquals("My Wishlist", wishlist.getWishlistName());

    }

    @Test
    void getUser() {
        assertEquals(userEntity, wishlist.getUser());
    }

    @Test
    void setWishlistId() {
        // Test setters
        Long newWishlistId = 1L;
        wishlist.setWishlistId(newWishlistId);
        assertEquals(newWishlistId, wishlist.getWishlistId());
    }

    @Test
    void setWishlistName() {
        wishlist.setWishlistName("My New Wishlist");
        assertEquals("My New Wishlist", wishlist.getWishlistName());
    }

    @Test
    void setUser() {
        UserEntity newUserEntity = new UserEntity("jane_doe", "password123", "Jane Doe", "jane.doe@example.com", LocalDate.of(1995, 1, 1));
        wishlist.setUser(newUserEntity);
        assertEquals(newUserEntity, wishlist.getUser());
    }
}