package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.PrioritySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistProductModelUnitTests {

    private WishlistProduct wishlistProduct;

    private UserEntity userEntity;

    private Wishlist wishlist;

    private Product product;


    @BeforeEach
    void setUp() {
        // Create a sample wishlist for testing
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        wishlist = new Wishlist("My Wishlist", userEntity);
        product = new Product(userEntity, "testProduct", 15, "https://URL", "https://imageURL", PrioritySelection.DESIRABLE, "description");
        wishlistProduct = new WishlistProduct(wishlist, product);
    }

    @Test
    void getWishlistProductId(){
        assertNull(wishlistProduct.getWishlistProductId());
    }

    @Test
    void setWishlistProductId() {
        Long newWishlistProductId = 1L;
        wishlistProduct.setWishlistProductId(newWishlistProductId);
        assertEquals(newWishlistProductId, wishlistProduct.getWishlistProductId());
    }

    @Test
    void getWishlist(){
        assertEquals(wishlist, wishlistProduct.getWishlist());
    }

    @Test
    void setWishlist(){
        Wishlist newWishlist = new Wishlist("newWishlist", userEntity);
        wishlistProduct.setWishlist(newWishlist);
        assertEquals(newWishlist, wishlistProduct.getWishlist());
    }

    @Test
    void getProduct() {
        assertEquals(product, wishlistProduct.getProduct());
    }

    @Test
    void setProduct() {
        Product newProduct = new Product(userEntity, "newProduct", 20, "URL", "imageURL", PrioritySelection.IMPORTANT, "description");
        wishlistProduct.setProduct(newProduct);
        assertEquals(newProduct, wishlistProduct.getProduct());
    }

    @Test
    void isPurchased() {
        assertFalse(wishlistProduct.isPurchased());
    }

    @Test
    void setPurchased() {
        wishlistProduct.setPurchased(true);
        assertTrue(wishlistProduct.isPurchased());
    }
}
