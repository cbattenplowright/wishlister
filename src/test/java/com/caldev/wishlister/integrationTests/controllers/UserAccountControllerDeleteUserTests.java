package com.caldev.wishlister.integrationTests.controllers;

import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.exceptions.UserNotFoundException;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerDeleteUserTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

    UserAccount testUserAccount;

    @BeforeEach
    void setUp(){
        testUserAccount = new UserAccount("test@email.com", "test", "test", LocalDate.now(), new HashSet<>(List.of(new Authority("ROLE_ADMIN"))));
    }

    @Test
    void whenUserIsAuthenticatedAndAuthorized_thenReturn200() throws Exception {
        UUID testUserId = UUID.randomUUID();
        doNothing().when(userService).deleteUser(testUserId);
        this.mockMvc.perform(delete("/api/users/{requestedId}", testUserId)
                        .with(user(testUserAccount)))
                .andExpect(status().isOk());
    }

    @Test
    void whenUserIsNotAuthorized_thenReturn401() throws Exception {

        this.mockMvc.perform(delete("/api/users/{requestedId}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenUserIdDoesNotExistAndUserIsAuthenticatedAndAuthorized_thenReturn404() throws Exception {

        UUID testUserId = UUID.randomUUID();

        doThrow(new UserNotFoundException("User not found with id: " + testUserId)).when(userService).deleteUser(testUserId);

        when(userService.getUserById(testUserId)).thenReturn(null);

        this.mockMvc.perform(delete("/api/users/{requestedId}", testUserId)
                        .with(user(testUserAccount)))
                .andExpect(status().isNotFound());
    }
}
