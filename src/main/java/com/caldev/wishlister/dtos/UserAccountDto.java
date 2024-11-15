package com.caldev.wishlister.dtos;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class UserAccountDto {
    private UUID userAccountId;
    @Email(message = "This field must be a valid email address")
    private String email;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private ArrayList<Long> authorityIds;
    private ArrayList<Long> wishlistIds;
    private ArrayList<Long> productIds;

    public UserAccountDto() {

    }

    public UserAccountDto(UUID userAccountId, String email, String password, String name, LocalDate dateOfBirth, ArrayList<Long> authorityIds, ArrayList<Long> wishlistIds, ArrayList<Long> productIds) {
        this.userAccountId = userAccountId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.authorityIds = authorityIds;
        this.wishlistIds = wishlistIds;
        this.productIds = productIds;
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

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(UUID userAccountId) {
        this.userAccountId = userAccountId;
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
