package com.caldev.wishlister.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class NewUserDto {

    @NotNull(message = "This field cannot be empty")
    @Email(message = "This field must be a valid email address")
    private String email;
    @NotNull(message = "This field cannot be empty")
    private String password;
    @NotNull(message = "This field cannot be empty")
    private String name;
    @NotNull(message = "This field cannot be empty")
    private LocalDate dateOfBirth;


    public NewUserDto() {
    }

    public NewUserDto(String email,String password, String name, LocalDate dateOfBirth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
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

    @Override
    public String toString() {
        return "NewUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
