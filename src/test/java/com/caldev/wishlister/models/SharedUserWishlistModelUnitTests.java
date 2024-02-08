package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedUserWishlistModelUnitTests {

    private SharedUserWishlist sharedUserWishlist;

    private Wishlist wishlist;

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        wishlist = new Wishlist("Wishlist 1", userEntity);
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        sharedUserWishlist = new SharedUserWishlist(userEntity, wishlist);
        sharedUserWishlist.setSharedUserWishlistId(1L);
    }

    @Test
    public void testGetSharedUserWishlistId() {
        assertEquals(1L, sharedUserWishlist.getSharedUserWishlistId());
    }

    @Test
    public void testSetSharedUserWishlistId() {
        Long newSharedUserWishlistId = 2L;
        sharedUserWishlist.setSharedUserWishlistId(newSharedUserWishlistId);
        assertEquals(2L, sharedUserWishlist.getSharedUserWishlistId());
    }

    @Test
    public void testGetSharedUser() {
        assertEquals(userEntity, sharedUserWishlist.getSharedUser());
    }

    @Test
    public void testSetSharedUser() {
        UserEntity newSharedUser = new UserEntity("jane_doe", "password123", "Jane Doe", "jane.doe@example.com", LocalDate.of(1995, 1, 1));
        sharedUserWishlist.setSharedUser(newSharedUser);
        assertEquals(newSharedUser, sharedUserWishlist.getSharedUser());
    }

    @Test
    public void testGetWishlist() {
        assertEquals(wishlist, sharedUserWishlist.getWishlist());
    }

    @Test
    public void testSetWishlist() {
        Wishlist newWishlist = new Wishlist("Wishlist 2", userEntity);
        sharedUserWishlist.setWishlist(newWishlist);
        assertEquals(newWishlist, sharedUserWishlist.getWishlist());
    }

    @Test
    public void testToString() {
        String expectedToString = "SharedUserWishlist{" +
                "sharedUserWishlistId=" + sharedUserWishlist.getSharedUserWishlistId() +
                ", sharedUser=" + sharedUserWishlist.getSharedUser() +
                ", wishlist=" + sharedUserWishlist.getWishlist() +
                '}';
        assertEquals(expectedToString, sharedUserWishlist.toString());
    }
}