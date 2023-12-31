package com.caldev.wishlister.unitTests.models;

import com.caldev.wishlister.models.SecurityUserDetails;
import com.caldev.wishlister.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserDetailsUnitTests {

    private SecurityUserDetails securityUserDetails;
    private User user;


    @BeforeEach
    void setUp() {
        user = new User("john_doe", "password123", "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        securityUserDetails = new SecurityUserDetails(user);
    }


    @Test
    void getUser() {
        assertEquals(user, securityUserDetails.getUser());
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