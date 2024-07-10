package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long wishlistId;
    @Column(nullable = false)
    private String wishlistName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"wishlists"})
    private UserAccount userAccount;
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"wishlist"})
    private List<WishlistProduct> wishlistProducts;

    protected Wishlist(){}

    public Wishlist(String wishlistName, UserAccount userAccount, List<WishlistProduct> wishlistProducts) {
        this.wishlistName = wishlistName;
        this.userAccount = userAccount;
        this.wishlistProducts = wishlistProducts;
//TODO edit the unit tests to accomodated the WishlistProduct
    }

    public Long getId() {
        return wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public UserAccount getUser() {
        return userAccount;
    }

    public void setUser(UserAccount userAccount) {
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
                ", user=" + userAccount +
                '}';
    }
}
