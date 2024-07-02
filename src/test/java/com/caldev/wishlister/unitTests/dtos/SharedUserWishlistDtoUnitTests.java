package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.SharedUserWishlistDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedUserWishlistDtoUnitTests {

    private SharedUserWishlistDto sharedUserWishlistDto;
    private UUID userId;

    @BeforeEach
    public void setUp(){
        userId = UUID.randomUUID();
        sharedUserWishlistDto = new SharedUserWishlistDto(1L, userId, 1L);
    }

    @Test
    public void shouldGetSharedUserWishlistId() {
        assertThat(sharedUserWishlistDto.getSharedUserWishlistId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetUserId() {
        assertThat(sharedUserWishlistDto.getUserId()).isEqualTo(this.userId);
    }

    @Test
    public void shouldGetWishlistId(){
        assertThat(sharedUserWishlistDto.getWishlistId()).isEqualTo(1L);
    }

    @Test
    public void shouldSetSharedUserWishlistId(){
        sharedUserWishlistDto.setSharedUserWishlistId(2L);
        assertThat(sharedUserWishlistDto.getSharedUserWishlistId()).isEqualTo(2L);
    }

    @Test
    public void shouldSetUserId(){
        UUID newUserId = UUID.randomUUID();
        sharedUserWishlistDto.setUserId(newUserId);
        assertThat(sharedUserWishlistDto.getUserId()).isEqualTo(newUserId);
    }

    @Test
    public void shouldSetWishlistId(){
        sharedUserWishlistDto.setWishlistId(2L);
        assertThat(sharedUserWishlistDto.getWishlistId()).isEqualTo(2L);
    }

    @Test
    public void shouldToString(){
        String toString = sharedUserWishlistDto.toString();
        assertThat(toString).isEqualTo("SharedUserWishlistDto{sharedUserWishlistId=1, userId=" + userId + ", wishlistId=1}");
    }
}
