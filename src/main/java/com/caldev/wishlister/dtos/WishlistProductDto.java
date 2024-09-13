package com.caldev.wishlister.dtos;

import jakarta.validation.constraints.NotNull;

public class WishlistProductDto {

    @NotNull
    private Long wishlistId;

    @NotNull
    private Long productId;

    @NotNull
    private boolean isPurchased;

    public WishlistProductDto() {

    }

    public WishlistProductDto(Long wishlistId, Long productId, boolean isPurchased) {
        this.wishlistId = wishlistId;
        this.productId = productId;
        this.isPurchased = isPurchased;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    @Override
    public String toString() {
        return "WishlistProductDto{" +
                "wishlistId=" + wishlistId +
                ", productId=" + productId +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
