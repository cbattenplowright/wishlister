package com.caldev.wishlister.entities;

import jakarta.persistence.*;

@Entity
@Table(name="shared_wishlists")
public class SharedWishlist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="shared_wishlist_id")
    private Long sharedWishlistId;

//    TODO add join column to wishlist_name
    @Column(name = "wishlist_name")
    private String wishlistName;

    @ManyToOne
    @JoinColumn(name="shared_user_id")
    private UserAccount sharedUserAccount;

    @ManyToOne
    @JoinColumn(name="owner_user_id")
    private UserAccount ownerUserAccount;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist sharedWishlist;

    protected SharedWishlist() {}

    public SharedWishlist(UserAccount sharedUserAccount, Wishlist sharedWishlist) {
        this.sharedUserAccount = sharedUserAccount;
        this.sharedWishlist = sharedWishlist;
    }

    public SharedWishlist(String wishlistName, UserAccount sharedUserAccount, Wishlist sharedWishlist, UserAccount ownerUserAccount) {
        this.wishlistName = wishlistName;
        this.sharedUserAccount = sharedUserAccount;
        this.sharedWishlist = sharedWishlist;
        this.ownerUserAccount = ownerUserAccount;
    }

    public Long getSharedWishlistId() {
        return sharedWishlistId;
    }

    public void setSharedWishlistId(Long sharedWishlistId) {
        this.sharedWishlistId = sharedWishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public UserAccount getSharedUser() {
        return sharedUserAccount;
    }

    public void setSharedUser(UserAccount sharedUserAccount) {
        this.sharedUserAccount = sharedUserAccount;
    }

    public Wishlist getSharedWishlist() {
        return sharedWishlist;
    }

    public void setSharedWishlist(Wishlist sharedWishlist) {
        this.sharedWishlist = sharedWishlist;
    }

    public UserAccount getOwnerUser() {
        return ownerUserAccount;
    }

    public void setOwnerUser(UserAccount ownerUserAccount) {
        this.ownerUserAccount = ownerUserAccount;
    }

    @Override
    public String toString() {
        return "SharedWishlist{" +
                "sharedWishlistId=" + sharedWishlistId +
                ", sharedUser=" + sharedUserAccount +
                ", sharedWishlist=" + sharedWishlist +
                '}';
    }
}
