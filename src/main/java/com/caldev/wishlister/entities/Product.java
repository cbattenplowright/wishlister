package com.caldev.wishlister.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "product_id")
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
    private PrioritySelection priority;

    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    @JsonIgnore
    private UserAccount userAccount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("product")
    private List<WishlistProduct> wishlistProducts;

    protected Product() {
    }

    public Product(String productName, int price, URL url, URL imageUrl, String description, PrioritySelection priority, LocalDate dateAdded, List<WishlistProduct> wishlistProducts, UserAccount userAccount) {
        this.productName = productName;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.priority = priority;
        this.dateAdded = dateAdded;
        this.wishlistProducts = wishlistProducts;
        this.userAccount = userAccount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public PrioritySelection getPriority() {
        return priority;
    }

    public void setPriority(PrioritySelection priority) {
        this.priority = priority;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<WishlistProduct> getWishlistProducts() {
        return wishlistProducts;
    }

    public void setWishlistProducts(List<WishlistProduct> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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
                ", wishlistProducts=" + wishlistProducts +
                ", userAccount=" + userAccount.getEmail() +
                '}';
    }
}
