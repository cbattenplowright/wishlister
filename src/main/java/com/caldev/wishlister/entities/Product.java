package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column()
    private int price;
    @Column
    private URL url;
    @Column
    private URL imageUrl;
    @Column
    private String description;
    @Column
    private PrioritySelections priority;
    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;
    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id")
    )
    @JsonIgnoreProperties({"products"})
    private List<Wishlist> wishlist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"products"})
    private User user;

    protected Product() {
    }

    public Product(String productName, int price, URL url, URL imageUrl, String description, PrioritySelections priority, LocalDate dateAdded, List<Wishlist> wishlist, User user) {
        this.productName = productName;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.priority = priority;
        this.dateAdded = dateAdded;
        this.wishlist = wishlist;
        this.user = user;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrioritySelections getPriority() {
        return priority;
    }

    public void setPriority(PrioritySelections priority) {
        this.priority = priority;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Wishlist> wishlist) {
        this.wishlist = wishlist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", url=" + url +
                ", imageUrl=" + imageUrl +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dateAdded=" + dateAdded +
                ", wishlist=" + wishlist +
                ", user=" + user +
                '}';
    }
}
