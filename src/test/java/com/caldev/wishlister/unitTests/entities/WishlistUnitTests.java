package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WishlistUnitTests {

    private Wishlist wishlist;
    private UserAccount userAccount;

    @BeforeEach
    public void setUp(){
        userAccount = new UserAccount("user@email.com", "password", "name", LocalDate.of(2000, 1, 1), null);
        wishlist = new Wishlist("wishlistName", userAccount, null);
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
        assertThat(wishlist.getUserAccount()).isEqualTo(userAccount);
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
        UserAccount newUserAccount = new UserAccount("newUser@email.com", "newPassword", "newName", LocalDate.of(2000, 1, 1), null);
        wishlist.setUserAccount(newUserAccount);
        assertThat(wishlist.getUserAccount()).isEqualTo(newUserAccount);
    }

    @Test
    public void shouldToString(){
        assertThat(wishlist.toString()).isEqualTo("Wishlist{wishlistId=null, wishlistName='wishlistName', user=" + userAccount + "}");
    }
}
