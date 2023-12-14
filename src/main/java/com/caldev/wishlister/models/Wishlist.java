package com.caldev.wishlister.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @Column(name = "wishlist_name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonIgnoreProperties("{wishlists}")
    private User user;

    public Wishlist() {}

    public Wishlist(String wishlistName, User user) {
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
