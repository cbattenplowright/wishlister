package com.caldev.wishlister.models;

import jakarta.persistence.*;

@Entity
@Table(name = "shared_user_wishlists")
public class SharedUserWishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shared_user_wishlist_id")
    private Long sharedUserWishlistId;

    @Column(name = "shared_user_id", nullable = false)
    private UserEntity sharedUser;

    @Column(name = "wishlist_id", nullable = false)
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

    @Override
    public String toString() {
        return "SharedUserWishlist{" +
                "sharedUserWishlistId=" + sharedUserWishlistId +
                ", sharedUser=" + sharedUser +
                ", wishlist=" + wishlist +
                '}';
    }
}
