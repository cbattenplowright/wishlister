package com.caldev.wishlister.unitTests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.User;
import com.caldev.wishlister.models.Wishlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class UserModelUnitTests {

    private User user;

    @BeforeEach
    public void setUp() {
        // Create a sample user for testing
        user = new User("john_doe", "password123", "John Doe", "john.doe@example.com",
                LocalDate.of(1990, 1, 1));
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertNull(user.getUserId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
        assertNull(user.getWishlists());
        assertEquals(1, user.getRoles().size());
        assertEquals(RoleName.ROLE_USER, user.getRoles().iterator().next().getRole());

        // Test setters
        UUID newUserId = UUID.randomUUID();
        user.setUserId(newUserId);
        assertEquals(newUserId, user.getUserId());

        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());

        user.setPassword("new_password");
        assertEquals("new_password", user.getPassword());

        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());

        user.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getEmail());

        LocalDate newDateOfBirth = LocalDate.of(1995, 2, 2);
        user.setDateOfBirth(newDateOfBirth);
        assertEquals(user.getDateOfBirth(), newDateOfBirth);

        List<Wishlist> wishlists = List.of(new Wishlist("Wishlist1", user), new Wishlist("Wishlist2", user));
        user.setWishlists(wishlists);
        assertEquals(wishlists, user.getWishlists());

        Set<Role> newRoles = new HashSet<>();
        newRoles.add(new Role(RoleName.ROLE_ADMIN));
        user.setRoles(newRoles);
        assertEquals(newRoles, user.getRoles());
    }

    @Test
    void testToString() {
        String expectedToString = "User{" +
                "userId=null, " +
                "username='john_doe', " +
                "password='password123', " +
                "name='John Doe', " +
                "email='john.doe@example.com', " +
                "dateOfBirth=1990-01-01, " +
                "wishlists=null, " +
                "roles=[Role{roleId=null, roleName=ROLE_USER}]" +
                "}";
        assertEquals(expectedToString, user.toString());
    }
}