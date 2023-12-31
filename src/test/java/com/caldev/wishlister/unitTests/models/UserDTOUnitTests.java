package com.caldev.wishlister.unitTests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.models.UserDTO;

class UserDTOUnitTests {

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("john_doe",
                "password123",
                "John Doe",
                "john@gmail.com",
                LocalDate.of(1989, 12, 12));
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals("john_doe", userDTO.getUsername());
        assertEquals("password123", userDTO.getPassword());
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john@gmail.com", userDTO.getEmail());
        assertEquals(LocalDate.of(1989, 12, 12), userDTO.getDateOfBirth());

        userDTO.setUsername("Robin450");
        assertEquals("Robin450", userDTO.getUsername());

        userDTO.setPassword("new_password");
        assertEquals("new_password", userDTO.getPassword());

        userDTO.setName("Robin");
        assertEquals("Robin", userDTO.getName());

        userDTO.setEmail("robin@gmail.com");
        assertEquals("robin@gmail.com", userDTO.getEmail());

        userDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        assertEquals(LocalDate.of(1990, 1, 1), userDTO.getDateOfBirth());
    }

    @Test
    void testToString() {
        String expected = "UserDTO{username='john_doe', password='password123', name='John Doe', email='john@gmail.com', dateOfBirth='1989-12-12'}";
        assertEquals(expected, userDTO.toString());
    };
}
