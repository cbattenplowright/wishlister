package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.WishlistProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WishlistProductDtoUnitTests {

    private WishlistProductDto wishlistProductDto;

    @BeforeEach
    public void setUp(){
        wishlistProductDto = new WishlistProductDto(1L, 1L, false);
    }

    @Test
    public void shouldGetWishlistId() {
        Long wishlistId = wishlistProductDto.getWishlistId();
        assertThat(wishlistId).isEqualTo(1L);
    }

    @Test
    public void shouldGetProductId() {
        Long productId = wishlistProductDto.getProductId();
        assertThat(productId).isEqualTo(1L);
    }

    @Test
    public void shouldGetPurchased() {
        boolean purchased = wishlistProductDto.isPurchased();
        assertThat(purchased).isFalse();
    }

    @Test
    public void shouldSetWishlistId(){
        wishlistProductDto.setWishlistId(1L);
        assertThat(wishlistProductDto.getWishlistId()).isEqualTo(1L);
    }

    @Test
    public void shouldSetProductId(){
        wishlistProductDto.setProductId(1L);
        assertThat(wishlistProductDto.getProductId()).isEqualTo(1L);
    }

    @Test
    public void shouldSetPurchased(){
        wishlistProductDto.setPurchased(true);
        assertThat(wishlistProductDto.isPurchased()).isTrue();
    }

    @Test
    public void shouldToString(){
        String toString = wishlistProductDto.toString();
        assertThat(toString).isEqualTo("WishlistProductDto{wishlistId=1, productId=1, isPurchased=false}");
    }

}
