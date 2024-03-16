package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.PrioritySelection;

public class ProductDTO {

    private Long userEntityId;

    private String productName;

    private int price;

    private String url;

    private String imageUrl;


    // TODO How do I map a priority passed in to the BE that is an enum?
    private PrioritySelection priority;

    private String description;

    public ProductDTO() {

    }

    public ProductDTO(Long userEntityId, String productName, int price, String url, String imageUrl, PrioritySelection priority, String description) {
        this.userEntityId = userEntityId;
        this.productName = productName;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.description = description;
    }

    public Long getUserEntityId (){
        return userEntityId;
    }

    public void setUserEntityId(Long userEntityId){
        this.userEntityId = userEntityId;
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

    @Override
    public String toString() {
        return "ProductDTO{" +
                "userEntityId=" + userEntityId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }
}
