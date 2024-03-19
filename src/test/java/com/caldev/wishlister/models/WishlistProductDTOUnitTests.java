package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistProductDTOUnitTests {

    private WishlistProductDTO wishlistProductDTO;

    @BeforeEach
    void setUp(){
        wishlistProductDTO = new WishlistProductDTO(23L, 67382L, true);
    }

    @Test
    void getWishlistId() {
        assertEquals(23L, wishlistProductDTO.getWishlistId());
    }

    @Test
    void setWishlistId() {
        Long newWishlistId = 24L;
        wishlistProductDTO.setWishlistId(newWishlistId);
        assertEquals(newWishlistId, wishlistProductDTO.getWishlistId());
    }

    @Test
    void getProductId(){
        assertEquals(67382L, wishlistProductDTO.getProductId());
    }

    @Test
    void setProductId(){
        Long newProductId = 13L;
        wishlistProductDTO.setProductId(13L);
        assertEquals(13L, wishlistProductDTO.getProductId());
    }

    @Test
    void isPurchased(){
        assertTrue(wishlistProductDTO.isPurchased());
    }

    @Test
    void setIsPurchased(){
        wishlistProductDTO.setIsPurchased(false);
        assertFalse(wishlistProductDTO.isPurchased());
    }

    @Test
    void testToString(){
        assertEquals("WishlistProductDTO{wishlistId=23, productId=67382, isPurchased=true}", wishlistProductDTO.toString());
    }
}
