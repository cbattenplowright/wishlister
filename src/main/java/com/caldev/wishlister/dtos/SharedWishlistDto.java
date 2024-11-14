package com.caldev.wishlister.dtos;

import java.util.UUID;

public class SharedWishlistDto {

    private Long sharedUserWishlistId;
    private UUID userId;
    private Long wishlistId;

    public SharedWishlistDto(){

    }

    public SharedWishlistDto(Long sharedUserWishlistId, UUID userId, Long wishlistId){
      this.sharedUserWishlistId = sharedUserWishlistId;
      this.userId = userId;
      this.wishlistId = wishlistId;
    }

    public Long getSharedUserWishlistId() {
        return sharedUserWishlistId;
    }

    public void setSharedUserWishlistId(Long sharedUserWishlistId) {
        this.sharedUserWishlistId = sharedUserWishlistId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    @Override
    public String toString() {
        return "SharedUserWishlistDto{" +
                "sharedUserWishlistId=" + sharedUserWishlistId +
                ", userId=" + userId +
                ", wishlistId=" + wishlistId +
                '}';
    }
}
