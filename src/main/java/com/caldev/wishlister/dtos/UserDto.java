package com.caldev.wishlister.dtos;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserDto {

    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private ArrayList<Long> roleIds;
    private ArrayList<Long> wishlistIds;
    private ArrayList<Long> productIds;

    public UserDto() {

    }

    public UserDto(String username, String password, String name, String email, LocalDate dateOfBirth, ArrayList<Long> roleIds, ArrayList<Long> wishlistIds, ArrayList<Long> productIds) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.roleIds = roleIds;
        this.wishlistIds = wishlistIds;
        this.productIds = productIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(ArrayList<Long> roleIds) {
        this.roleIds = roleIds;
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
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", roleIds=" + roleIds +
                ", wishlistIds=" + wishlistIds +
                ", productIds=" + productIds +
                '}';
    }
}
