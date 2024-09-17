package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_products")
public class WishlistProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_product_id")
    private Long wishlistProductId;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    @JsonIgnoreProperties({"wishlistProducts"})
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"wishlistProducts"})
    private Product product;

    @Column(name = "is_purchased")
    private boolean isPurchased;

    protected WishlistProduct() {}

    public WishlistProduct(Wishlist wishlist, Product product, boolean isPurchased) {
        this.wishlist = wishlist;
        this.product = product;
        this.isPurchased = isPurchased;
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

    public Product getProduct() {
        return product;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setIsPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
