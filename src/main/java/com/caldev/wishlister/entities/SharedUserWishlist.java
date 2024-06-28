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
    private User sharedUser;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist sharedWishlist;

    protected SharedUserWishlist() {}

    public SharedUserWishlist(User sharedUser, Wishlist sharedWishlist) {
        this.sharedUser = sharedUser;
        this.sharedWishlist = sharedWishlist;
    }

    public Long getSharedUserWishlistId() {
        return sharedUserWishlistId;
    }

    public User getSharedUser() {
        return sharedUser;
    }

    public void setSharedUser(User sharedUser) {
        this.sharedUser = sharedUser;
    }

    public Wishlist getSharedWishlist() {
        return sharedWishlist;
    }

    public void setSharedWishlist(Wishlist sharedWishlist) {
        this.sharedWishlist = sharedWishlist;
    }

    @Override
    public String toString() {
        return "SharedUserWishlist{" +
                "sharedUserWishlistId=" + sharedUserWishlistId +
                ", sharedUser=" + sharedUser +
                ", sharedWishlist=" + sharedWishlist +
                '}';
    }
}
