package com.caldev.wishlister.unitTests.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.models.UserEntityDTO;

class UserEntityDTOUnitTests {

    private UserEntityDTO userEntityDTO;

    @BeforeEach
    void setUp() {
        userEntityDTO = new UserEntityDTO("john_doe",
                "password123",
                "John Doe",
                "john@gmail.com",
                LocalDate.of(1989, 12, 12));
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals("john_doe", userEntityDTO.getUsername());
        assertEquals("password123", userEntityDTO.getPassword());
        assertEquals("John Doe", userEntityDTO.getName());
        assertEquals("john@gmail.com", userEntityDTO.getEmail());
        assertEquals(LocalDate.of(1989, 12, 12), userEntityDTO.getDateOfBirth());

        userEntityDTO.setUsername("Robin450");
        assertEquals("Robin450", userEntityDTO.getUsername());

        userEntityDTO.setPassword("new_password");
        assertEquals("new_password", userEntityDTO.getPassword());

        userEntityDTO.setName("Robin");
        assertEquals("Robin", userEntityDTO.getName());

        userEntityDTO.setEmail("robin@gmail.com");
        assertEquals("robin@gmail.com", userEntityDTO.getEmail());

        userEntityDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        assertEquals(LocalDate.of(1990, 1, 1), userEntityDTO.getDateOfBirth());
    }

    @Test
    void testToString() {
        String expected = "UserDTO{username='john_doe', password='password123', name='John Doe', email='john@gmail.com', dateOfBirth='1989-12-12'}";
        assertEquals(expected, userEntityDTO.toString());
    };
}
