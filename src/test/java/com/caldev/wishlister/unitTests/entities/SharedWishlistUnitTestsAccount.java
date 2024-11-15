package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.SharedWishlist;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedWishlistUnitTestsAccount {

    private UserAccount sharedUserAccount;
    private Wishlist wishlist;
    private SharedWishlist sharedWishlist;

    @BeforeEach
    public void setUp(){
        sharedUserAccount = new UserAccount("sharedUser@email.com", "testPassword", "testName", LocalDate.of(2000, 1, 1), null);
        wishlist = new Wishlist("testWishlist", sharedUserAccount, null);
        sharedWishlist = new SharedWishlist(sharedUserAccount, wishlist);
    }

    @Test
    public void shouldGetSharedWishlistIdTest(){
        assertThat(sharedWishlist.getSharedWishlistId()).isNull();
    }

    @Test
    public void shouldGetSharedUserTest(){
        assertThat(sharedWishlist.getSharedUser()).isEqualTo(sharedUserAccount);
    }

    @Test
    public void shouldGetSharedWishlistTest(){
        assertThat(sharedWishlist.getSharedWishlist()).isEqualTo(wishlist);
    }

    @Test
    public void shouldSetSharedUserTest(){
        UserAccount userAccount = new UserAccount("sharedUser2@email.com", "testPassword", "testName", LocalDate.of(2000, 1, 1), null);
        sharedWishlist.setSharedUser(userAccount);
        assertThat(sharedWishlist.getSharedUser()).isEqualTo(userAccount);
    }

    @Test
    public void shouldSetSharedWishlistTest() {
        Wishlist newSharedWishlist = new Wishlist("testWishlist2", sharedUserAccount, null);
        sharedWishlist.setSharedWishlist(newSharedWishlist);
        assertThat(sharedWishlist.getSharedWishlist()).isEqualTo(newSharedWishlist);

    }

    @Test
    public void shouldToStringTest(){
        assertThat(wishlist.toString()).isEqualTo("SharedUserWishlist{sharedUserWishlistId=null, sharedUser=" + sharedUserAccount + ", sharedWishlist=" + wishlist +"}");
    }
}
