package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String wishlistName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"wishlists"})
    private User user;

    protected Wishlist(){}

    public Wishlist(String wishlistName, User user) {
        this.wishlistName = wishlistName;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
