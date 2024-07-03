package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.entities.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WishlistUnitTests {

    private Wishlist wishlist;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User("username", "password", "name", "email@email.com", LocalDate.of(2000, 1, 1), null);
        wishlist = new Wishlist("wishlistName", user, null);
    }

    @Test
    public void shouldGetId(){
        assertNull(wishlist.getId());
    }

    @Test
    public void shouldGetWishlistName(){
        assertThat(wishlist.getWishlistName()).isEqualTo("wishlistName");
    }

    @Test
    public void shouldGetWishlistUser(){
        assertThat(wishlist.getUser()).isEqualTo(user);
    }

    @Test
    public void shouldSetWishlistName(){
        wishlist.setWishlistName("newWishlistName");
        assertThat(wishlist.getWishlistName()).isEqualTo("newWishlistName");
    }

    @Test
    public void shouldGetWishlistProducts(){
        assertNull(wishlist.getWishlistProducts());
    }

    @Test
    public void shouldSetWishlistProducts(){
        wishlist.setWishlistProducts(null);
        assertNull(wishlist.getWishlistProducts());
    }

    @Test
    public void shouldSetWishlistUser(){
        User newUser = new User("newUsername", "newPassword", "newName", "newEmail@email.com", LocalDate.of(2000, 1, 1), null);
        wishlist.setUser(newUser);
        assertThat(wishlist.getUser()).isEqualTo(newUser);
    }

    @Test
    public void shouldToString(){
        assertThat(wishlist.toString()).isEqualTo("Wishlist{wishlistId=null, wishlistName='wishlistName', user=" + user + "}");
    }
}
