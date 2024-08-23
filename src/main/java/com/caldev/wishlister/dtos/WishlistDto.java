package com.caldev.wishlister.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WishlistDto {

    @NotNull(message = "The userId field cannot be empty")
    private UUID userId;

    @NotNull(message = "The wishlistName cannot be empty")
    private String wishlistName;

    public WishlistDto() {
    }

    public WishlistDto(UUID userId, String wishlistName) {
        this.userId = userId;
        this.wishlistName = wishlistName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    @Override
    public String toString() {
        return "WishlistDto{" +
                "userId=" + userId +
                ", wishlistName='" + wishlistName + '\'' +
                '}';
    }
}
