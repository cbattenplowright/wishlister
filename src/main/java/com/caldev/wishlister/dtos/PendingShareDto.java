package com.caldev.wishlister.dtos;

import java.util.UUID;

public class PendingShareDto {

    private Long pendingShareId;
    private String token;
    private Long wishlistId;
    private UUID senderUserId;
    private String recipientEmail;

    public PendingShareDto() {

    }

    public PendingShareDto(Long pendingShareId, String token, Long wishlistId, UUID senderUserId, String recipientEmail) {
        this.pendingShareId = pendingShareId;
        this.token = token;
        this.wishlistId = wishlistId;
        this.senderUserId = senderUserId;
        this.recipientEmail = recipientEmail;
    }

    public Long getPendingShareId() {
        return pendingShareId;
    }

    public void setPendingShareId(Long pendingShareId) {
        this.pendingShareId = pendingShareId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public UUID getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(UUID senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}
