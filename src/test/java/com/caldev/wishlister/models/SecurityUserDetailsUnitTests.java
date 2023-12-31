package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserDetailsUnitTests {

    private SecurityUserDetails securityUserDetails;
    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        securityUserDetails = new SecurityUserDetails(userEntity);
    }


    @Test
    void getUser() {
        assertEquals(userEntity, securityUserDetails.getUser());
    }

    @Test
    void getAuthorities() {
        assertEquals(1, securityUserDetails.getAuthorities().size());
    }

    @Test
    void getPassword() {
        assertEquals("password123", securityUserDetails.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("john_doe", securityUserDetails.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(securityUserDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(securityUserDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(securityUserDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(securityUserDetails.isEnabled());
    }
}