package com.caldev.wishlister.dtos;

import java.util.UUID;

public class SharedWishlistDto {

    private Long sharedUserWishlistId;
    private UUID sharedUserId;
    private Long wishlistId;
    private UUID ownerUserId;

    public SharedWishlistDto(){

    }

    public SharedWishlistDto(Long sharedUserWishlistId, UUID sharedUserId, Long wishlistId, UUID ownerUserId){
      this.sharedUserWishlistId = sharedUserWishlistId;
      this.sharedUserId = sharedUserId;
      this.wishlistId = wishlistId;
      this.ownerUserId = ownerUserId;
    }

    public Long getSharedUserWishlistId() {
        return sharedUserWishlistId;
    }

    public void setSharedUserWishlistId(Long sharedUserWishlistId) {
        this.sharedUserWishlistId = sharedUserWishlistId;
    }

    public UUID getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(UUID sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(UUID ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Override
    public String toString() {
        return "SharedUserWishlistDto{" +
                "sharedUserWishlistId=" + sharedUserWishlistId +
                ", sharedUserId=" + sharedUserId +
                ", wishlistId=" + wishlistId +
                ", ownerUserId=" + ownerUserId +
                '}';
    }
}
