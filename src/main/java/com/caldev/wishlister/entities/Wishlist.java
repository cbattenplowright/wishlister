package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;
    @Column(nullable = false)
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    @JsonIgnore
    private UserAccount userAccount;

    @OneToMany(mappedBy = "wishlist")
    @JsonIgnoreProperties("wishlist")
    private List<WishlistProduct> wishlistProducts;

    protected Wishlist(){}

    public Wishlist(String wishlistName, UserAccount userAccount, List<WishlistProduct> wishlistProducts) {
        this.wishlistName = wishlistName;
        this.userAccount = userAccount;
        this.wishlistProducts = wishlistProducts;
    }

    public Long getId() {
        return wishlistId;
    }

    public void setId(Long id) {
        this.wishlistId = id;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<WishlistProduct> getWishlistProducts() {
        return wishlistProducts;
    }

    public void setWishlistProducts(List<WishlistProduct> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", wishlistName='" + wishlistName + '\'' +
                ", userAccount=" + userAccount.getEmail() +
                '}';
    }
}
