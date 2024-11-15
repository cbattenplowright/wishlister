package com.caldev.wishlister.dtos;

import com.caldev.wishlister.entities.PrioritySelection;
import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ProductDto {

    private Long productId;
    @NotNull(message = "The productName field cannot be empty")
    private String productName;
    @NotNull(message = "The userId field cannot be empty")
    private UUID userId;
    private int price;
    private URL url;
    private URL imageUrl;
    private PrioritySelection prioritySelection;
    private String description;
    private LocalDate dateAdded;
    private List<Long> wishlistProductIds;

    public ProductDto() {
    }

    public ProductDto(Long productId, String productName, UUID userId, int price, URL url, URL imageUrl, PrioritySelection prioritySelection, String description, LocalDate dateAdded, List<Long> wishlistProductIds) {
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.prioritySelection = prioritySelection;
        this.description = description;
        this.dateAdded = dateAdded;
        this.wishlistProductIds = wishlistProductIds;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public PrioritySelection getPrioritySelection() {
        return prioritySelection;
    }

    public void setPrioritySelection(PrioritySelection prioritySelection) {
        this.prioritySelection = prioritySelection;
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

    public List<Long> getWishlistProductIds() {
        return wishlistProductIds;
    }

    public void setWishlistProductIds(List<Long> wishlistProductIds) {
        this.wishlistProductIds = wishlistProductIds;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productName='" + productName + '\'' +
                ", userId=" + userId +
                ", price=" + price +
                ", url=" + url +
                ", imageUrl=" + imageUrl +
                ", prioritySelection=" + prioritySelection +
                ", description='" + description + '\'' +
                ", dateAdded=" + dateAdded +
                ", wishlistProductIds=" + wishlistProductIds +
                '}';
    }
}
