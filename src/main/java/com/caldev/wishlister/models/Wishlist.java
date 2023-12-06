package com.caldev.wishlister.models;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @Column(name = "wishlist_name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    public void Wishlist() {}

    public Wishlist(Long wishlistId, String wishlistName, User user) {
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.user = user;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public User getUser() {
        return user;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
