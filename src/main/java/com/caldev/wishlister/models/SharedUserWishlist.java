package com.caldev.wishlister.models;

import jakarta.persistence.*;

@Table(name = "shared_user_wishlists")
public class SharedUserWishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shared_user_wishlist_id")
    private Long sharedUserWishlistId;

    @Column(name = "shared_user_id")
    private UserEntity sharedUser;

    @Column(name = "wishlist_id")
    private Wishlist wishlist;

    public SharedUserWishlist() {
    }

    public SharedUserWishlist(UserEntity sharedUser, Wishlist wishlist) {
        this.sharedUser = sharedUser;
        this.wishlist = wishlist;
    }

    public Long getSharedUserWishlistId() {
        return sharedUserWishlistId;
    }

    public void setSharedUserWishlistId(Long sharedUserWishlistId) {
        this.sharedUserWishlistId = sharedUserWishlistId;
    }

    public UserEntity getSharedUser() {
        return sharedUser;
    }

    public void setSharedUser(UserEntity sharedUser) {
        this.sharedUser = sharedUser;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}
