package com.caldev.wishlister.entities;

import jakarta.persistence.*;

@Entity
@Table(name="shared_wishlists")
public class SharedWishlist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="shared_wishlist_id")
    private Long sharedWishlistId;

    @ManyToOne
    @JoinColumn(name="shared_user_id")
    private UserAccount sharedUserAccount;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist sharedWishlist;

    protected SharedWishlist() {}

    public SharedWishlist(UserAccount sharedUserAccount, Wishlist sharedWishlist) {
        this.sharedUserAccount = sharedUserAccount;
        this.sharedWishlist = sharedWishlist;
    }

    public Long getSharedWishlistId() {
        return sharedWishlistId;
    }

    public void setSharedWishlistId(Long sharedWishlistId) {
        this.sharedWishlistId = sharedWishlistId;
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


    public UserAccount getSharedUserAccount() {
        return sharedUserAccount;
    }

    public void setSharedUserAccount(UserAccount sharedUserAccount) {
        this.sharedUserAccount = sharedUserAccount;
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
