package com.caldev.wishlister.models;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_products")
public class WishlistProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_product_id")
    private Long wishlistProductId;

    @Column
    private Wishlist wishlist;

    @Column
    private Product product;

    @Column(name = "is_purchased")
    private boolean isPurchased;

    public WishlistProduct() {
    }

    public WishlistProduct(Long wishlistProductId, Wishlist wishlist, Product product) {
        this.wishlistProductId = wishlistProductId;
        this.wishlist = wishlist;
        this.product = product;
        this.isPurchased = false;
    }

    public Long getWishlistProductId() {
        return wishlistProductId;
    }

    public void setWishlistProductId(Long wishlistProductId) {
        this.wishlistProductId = wishlistProductId;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    @Override
    public String toString() {
        return "WishlistProduct{" +
                "wishlistProductId=" + wishlistProductId +
                ", wishlist=" + wishlist +
                ", product=" + product +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
