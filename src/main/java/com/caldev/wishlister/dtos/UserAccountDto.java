package com.caldev.wishlister.dtos;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserAccountDto {

    private String email;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private ArrayList<Long> authorityIds;
    private ArrayList<Long> wishlistIds;
    private ArrayList<Long> productIds;

    public UserAccountDto() {

    }

    public UserAccountDto(String email, String password, String name, LocalDate dateOfBirth, ArrayList<Long> authorityIds, ArrayList<Long> wishlistIds, ArrayList<Long> productIds) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.authorityIds = authorityIds;
        this.wishlistIds = wishlistIds;
        this.productIds = productIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Long> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(ArrayList<Long> authorityIds) {
        this.authorityIds = authorityIds;
    }

    public ArrayList<Long> getWishlistIds() {
        return wishlistIds;
    }

    public void setWishlistIds(ArrayList<Long> wishlistIds) {
        this.wishlistIds = wishlistIds;
    }

    public ArrayList<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(ArrayList<Long> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", authorityIds=" + authorityIds +
                ", wishlistIds=" + wishlistIds +
                ", productIds=" + productIds +
                '}';
    }
}
