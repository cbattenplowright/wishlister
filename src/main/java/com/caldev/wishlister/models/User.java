package com.caldev.wishlister.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column
    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists;

    public User(){
    }

    public User(String username, String password, String name, String email,LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // GETTERS AND SETTERS

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", wishlists=" + wishlists +
                '}';
    }
}