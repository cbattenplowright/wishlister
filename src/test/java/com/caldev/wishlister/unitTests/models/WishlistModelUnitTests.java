package com.caldev.wishlister.unitTests.models;

import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class WishlistModelUnitTests {

    private Wishlist wishlist;
    private User user;

    @BeforeEach
    void setUp() {
        // Create a sample wishlist for testing
        user = new User("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        wishlist = new Wishlist("My Wishlist", user);
    }

    @Test
    void getWishlistId() {
        // Test getters
        assertEquals(null, wishlist.getWishlistId());
    }

    @Test
    void getWishlistName() {
        assertEquals("My Wishlist", wishlist.getWishlistName());

    }

    @Test
    void getUser() {
        assertEquals(user, wishlist.getUser());
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
        User newUser = new User("jane_doe", "password123", "Jane Doe", "jane.doe@example.com", LocalDate.of(1995, 1, 1));
        wishlist.setUser(newUser);
        assertEquals(newUser, wishlist.getUser());
    }
}