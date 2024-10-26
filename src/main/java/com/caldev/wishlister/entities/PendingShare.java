package com.caldev.wishlister.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pending_share")
public class PendingShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_share_id")
    private Long pendingShareId;
    private String token;
    private String senderUserId;
    private Long wishlistId;
    private String recipientEmail;

    public PendingShare(String token, String senderUserId, Long wishlistId, String recipientEmail) {
        this.token = token;
        this.senderUserId = senderUserId;
        this.wishlistId = wishlistId;
        this.recipientEmail = recipientEmail;
    }

    public PendingShare() {
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

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public String toString() {
        return "PendingShare{" +
                "pendingShareId=" + pendingShareId +
                ", token='" + token + '\'' +
                ", senderUserId='" + senderUserId + '\'' +
                ", wishlistId=" + wishlistId +
                ", recipientEmail='" + recipientEmail + '\'' +
                '}';
    }
}
