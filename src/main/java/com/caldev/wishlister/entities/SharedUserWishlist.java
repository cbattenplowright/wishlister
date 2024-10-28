package com.caldev.wishlister.entities;

import jakarta.persistence.*;

@Entity
@Table(name="shared_user_wishlists")
public class SharedUserWishlist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="shared_user_wishlist_id")
    private Long sharedUserWishlistId;

    @ManyToOne
    @JoinColumn(name="shared_user_id")
    private UserAccount sharedUserAccount;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist sharedWishlist;

    protected SharedUserWishlist() {}

    public SharedUserWishlist(UserAccount sharedUserAccount, Wishlist sharedWishlist) {
        this.sharedUserAccount = sharedUserAccount;
        this.sharedWishlist = sharedWishlist;
    }

    public Long getSharedUserWishlistId() {
        return sharedUserWishlistId;
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

    public void setSharedUserWishlistId(Long sharedUserWishlistId) {
        this.sharedUserWishlistId = sharedUserWishlistId;
    }

    public UserAccount getSharedUserAccount() {
        return sharedUserAccount;
    }

    public void setSharedUserAccount(UserAccount sharedUserAccount) {
        this.sharedUserAccount = sharedUserAccount;
    }

    @Override
    public String toString() {
        return "SharedUserWishlist{" +
                "sharedUserWishlistId=" + sharedUserWishlistId +
                ", sharedUser=" + sharedUserAccount +
                ", sharedWishlist=" + sharedWishlist +
                '}';
    }
}
