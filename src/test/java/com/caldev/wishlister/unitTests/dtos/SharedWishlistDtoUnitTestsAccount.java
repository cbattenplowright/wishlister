package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.SharedWishlistDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedWishlistDtoUnitTestsAccount {

    private SharedWishlistDto sharedWishlistDto;
    private UUID userId;
    private UUID ownerUserId;

    @BeforeEach
    public void setUp(){
        userId = UUID.randomUUID();
        sharedWishlistDto = new SharedWishlistDto(1L, userId, 1L, ownerUserId);
    }

    @Test
    public void shouldGetSharedWishlistId() {
        assertThat(sharedWishlistDto.getSharedUserWishlistId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetUserId() {
        assertThat(sharedWishlistDto.getSharedUserId()).isEqualTo(this.userId);
    }

    @Test
    public void shouldGetWishlistId(){
        assertThat(sharedWishlistDto.getWishlistId()).isEqualTo(1L);
    }

    @Test
    public void shouldSetSharedUserWishlistId(){
        sharedWishlistDto.setSharedUserWishlistId(2L);
        assertThat(sharedWishlistDto.getSharedUserWishlistId()).isEqualTo(2L);
    }

    @Test
    public void shouldSetUserId(){
        UUID newUserId = UUID.randomUUID();
        sharedWishlistDto.setSharedUserId(newUserId);
        assertThat(sharedWishlistDto.getSharedUserId()).isEqualTo(newUserId);
    }

    @Test
    public void shouldSetWishlistId(){
        sharedWishlistDto.setWishlistId(2L);
        assertThat(sharedWishlistDto.getWishlistId()).isEqualTo(2L);
    }

    @Test
    public void shouldToString(){
        String toString = sharedWishlistDto.toString();
        assertThat(toString).isEqualTo("SharedUserWishlistDto{sharedUserWishlistId=1, userId=" + userId + ", wishlistId=1}");
    }
}
