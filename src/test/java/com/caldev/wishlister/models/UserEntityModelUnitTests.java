package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.enums.RoleName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class UserEntityModelUnitTests {

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        // Create a sample user for testing
        userEntity = new UserEntity("john_doe", "password123", "John Doe", "john.doe@example.com",
                LocalDate.of(1990, 1, 1));
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertNull(userEntity.getUserId());
        assertEquals("john_doe", userEntity.getUsername());
        assertEquals("password123", userEntity.getPassword());
        assertEquals("John Doe", userEntity.getName());
        assertEquals("john.doe@example.com", userEntity.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), userEntity.getDateOfBirth());
        assertNull(userEntity.getWishlists());
        assertEquals(1, userEntity.getRoles().size());
        assertEquals(RoleName.ROLE_USER, userEntity.getRoles().iterator().next().getRole());

        // Test setters
        UUID newUserId = UUID.randomUUID();
        userEntity.setUserId(newUserId);
        assertEquals(newUserId, userEntity.getUserId());

        userEntity.setUsername("jane_doe");
        assertEquals("jane_doe", userEntity.getUsername());

        userEntity.setPassword("new_password");
        assertEquals("new_password", userEntity.getPassword());

        userEntity.setName("Jane Doe");
        assertEquals("Jane Doe", userEntity.getName());

        userEntity.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", userEntity.getEmail());

        LocalDate newDateOfBirth = LocalDate.of(1995, 2, 2);
        userEntity.setDateOfBirth(newDateOfBirth);
        assertEquals(userEntity.getDateOfBirth(), newDateOfBirth);

        List<Wishlist> wishlists = List.of(new Wishlist("Wishlist1", userEntity), new Wishlist("Wishlist2", userEntity));
        userEntity.setWishlists(wishlists);
        assertEquals(wishlists, userEntity.getWishlists());

        Set<Role> newRoles = new HashSet<>();
        newRoles.add(new Role(RoleName.ROLE_ADMIN));
        userEntity.setRoles(newRoles);
        assertEquals(newRoles, userEntity.getRoles());
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
        assertEquals(expectedToString, userEntity.toString());
    }
}