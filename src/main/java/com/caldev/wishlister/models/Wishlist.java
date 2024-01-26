package com.caldev.wishlister.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

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
    @JsonIgnoreProperties({"wishlists"})
    private UserEntity userEntity;

    public Wishlist() {}

    public Wishlist(String wishlistName, UserEntity userEntity) {
        this.wishlistName = wishlistName;
        this.userEntity = userEntity;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", wishlistName='" + wishlistName + '\'' +
                ", userEntity=" + userEntity +
                '}';
    }
}
