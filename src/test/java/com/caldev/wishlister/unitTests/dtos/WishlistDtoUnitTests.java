package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.WishlistDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WishlistDtoUnitTests {

    private WishlistDto wishlistDto;
    private UUID userId;

    @BeforeEach
    public void setUp(){
        userId = UUID.randomUUID();
        wishlistDto = new WishlistDto(userId, "wishlistName");
    }

    @Test
    public void shouldGetUserId() {
        UUID userId = wishlistDto.getUserId();
        assertThat(userId).isEqualTo(this.userId);
    }

    @Test
    public void shouldGetWishlistName() {
        String wishlistName = wishlistDto.getWishlistName();
        assertThat(wishlistName).isEqualTo("wishlistName");
    }

    @Test
    public void shouldSetUserId(){
        UUID newUserId = UUID.randomUUID();
        wishlistDto.setUserId(newUserId);
        assertThat(wishlistDto.getUserId()).isEqualTo(newUserId);
    }

    @Test
    public void shouldSetWishlistName(){
        String newWishlistName = "newWishlistName";
        wishlistDto.setWishlistName(newWishlistName);
        assertThat(wishlistDto.getWishlistName()).isEqualTo(newWishlistName);
    }

    @Test
    public void shouldToString(){
        String toString = wishlistDto.toString();
        assertThat(toString).isEqualTo("WishlistDto{userId=" + userId + ", wishlistName='wishlistName'}");
    }
}
