package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.SharedUserWishlist;
import com.caldev.wishlister.entities.User;
import com.caldev.wishlister.entities.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedUserWishlistUnitTests {

    private User sharedUser;
    private Wishlist sharedWishlist;
    private SharedUserWishlist sharedUserWishlist;

    @BeforeEach
    public void setUp(){
        sharedUser = new User("testUser", "testPassword", "testName", "testEmail", LocalDate.of(2000, 1, 1), null);
        sharedWishlist = new Wishlist("testWishlist", sharedUser, null);
        sharedUserWishlist = new SharedUserWishlist(sharedUser, sharedWishlist);
    }

    @Test
    public void shouldGetSharedUserWishlistIdTest(){
        assertThat(sharedUserWishlist.getSharedUserWishlistId()).isNull();
    }

    @Test
    public void shouldGetSharedUserTest(){
        assertThat(sharedUserWishlist.getSharedUser()).isEqualTo(sharedUser);
    }

    @Test
    public void shouldGetSharedWishlistTest(){
        assertThat(sharedUserWishlist.getSharedWishlist()).isEqualTo(sharedWishlist);
    }

    @Test
    public void shouldSetSharedUserTest(){
        User user = new User("testUser2", "testPassword", "testName", "testEmail", LocalDate.of(2000, 1, 1), null);
        sharedUserWishlist.setSharedUser(user);
        assertThat(sharedUserWishlist.getSharedUser()).isEqualTo(user);
    }

    @Test
    public void shouldSetSharedWishlistTest() {
        Wishlist newSharedWishlist = new Wishlist("testWishlist2", sharedUser, null);
        sharedUserWishlist.setSharedWishlist(newSharedWishlist);
        assertThat(sharedUserWishlist.getSharedWishlist()).isEqualTo(newSharedWishlist);

    }

    @Test
    public void shouldToStringTest(){
        assertThat(sharedUserWishlist.toString()).isEqualTo("SharedUserWishlist{sharedUserWishlistId=null, sharedUser=" + sharedUser + ", sharedWishlist=" + sharedWishlist +"}");
    }
}
