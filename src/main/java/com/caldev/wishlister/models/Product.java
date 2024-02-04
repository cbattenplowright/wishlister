package com.caldev.wishlister.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {
// TODO Product class

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String productName;

    @Column
    private int price;

    @Column
    private String url;

    @Column(name = "image_url")
    private String imageUrl;

    //TODO create wishlist priority enum
    @Column
    private PrioritySelection priority;

    @Column
    private String description;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    public Product(){
    }

    public Product(UserEntity user, String productName, int price, String url, String imageUrl, PrioritySelection priority, String description) {
        this.user = user;
        this.productName = productName;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.description = description;
        this.dateAdded = LocalDate.now();
    }

    // GETTERS AND SETTERS


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PrioritySelection getPriority() {
        return priority;
    }

    public void setPriority(PrioritySelection priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", user=" + user +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
