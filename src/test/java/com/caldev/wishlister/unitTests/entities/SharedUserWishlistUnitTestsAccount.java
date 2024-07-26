package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.SharedUserWishlist;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedUserWishlistUnitTestsAccount {

    private UserAccount sharedUserAccount;
    private Wishlist sharedWishlist;
    private SharedUserWishlist sharedUserWishlist;

    @BeforeEach
    public void setUp(){
        sharedUserAccount = new UserAccount("sharedUser@email.com", "testPassword", "testName", LocalDate.of(2000, 1, 1), null);
        sharedWishlist = new Wishlist("testWishlist", sharedUserAccount, null);
        sharedUserWishlist = new SharedUserWishlist(sharedUserAccount, sharedWishlist);
    }

    @Test
    public void shouldGetSharedUserWishlistIdTest(){
        assertThat(sharedUserWishlist.getSharedUserWishlistId()).isNull();
    }

    @Test
    public void shouldGetSharedUserTest(){
        assertThat(sharedUserWishlist.getSharedUser()).isEqualTo(sharedUserAccount);
    }

    @Test
    public void shouldGetSharedWishlistTest(){
        assertThat(sharedUserWishlist.getSharedWishlist()).isEqualTo(sharedWishlist);
    }

    @Test
    public void shouldSetSharedUserTest(){
        UserAccount userAccount = new UserAccount("sharedUser2@email.com", "testPassword", "testName", LocalDate.of(2000, 1, 1), null);
        sharedUserWishlist.setSharedUser(userAccount);
        assertThat(sharedUserWishlist.getSharedUser()).isEqualTo(userAccount);
    }

    @Test
    public void shouldSetSharedWishlistTest() {
        Wishlist newSharedWishlist = new Wishlist("testWishlist2", sharedUserAccount, null);
        sharedUserWishlist.setSharedWishlist(newSharedWishlist);
        assertThat(sharedUserWishlist.getSharedWishlist()).isEqualTo(newSharedWishlist);

    }

    @Test
    public void shouldToStringTest(){
        assertThat(sharedUserWishlist.toString()).isEqualTo("SharedUserWishlist{sharedUserWishlistId=null, sharedUser=" + sharedUserAccount + ", sharedWishlist=" + sharedWishlist +"}");
    }
}
