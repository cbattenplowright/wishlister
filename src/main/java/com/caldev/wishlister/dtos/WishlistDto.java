package com.caldev.wishlister.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WishlistDto {

    private Long wishlistId;

    @NotNull(message = "The userId field cannot be empty")
    private UUID userId;

    @NotNull(message = "The wishlistName cannot be empty")
    private String wishlistName;

    private List<ProductDto> products = new ArrayList<>();

    public WishlistDto() {
    }

    public WishlistDto(UUID userId, String wishlistName) {
        this.userId = userId;
        this.wishlistName = wishlistName;
    }

    public WishlistDto(Long wishlistId, UUID userId, String wishlistName) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.wishlistName = wishlistName;
    }

    public WishlistDto(Long wishlistId, UUID userId, String wishlistName, List<ProductDto> products) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.wishlistName = wishlistName;
        this.products = products;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
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

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "WishlistDto{" +
                "wishlistId=" + wishlistId +
                "userId=" + userId +
                ", wishlistName='" + wishlistName + '\'' +
                '}';
    }
}
