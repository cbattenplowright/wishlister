package com.caldev.wishlister.integrationTests.controllers;

import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerPatchTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

    UserAccount testUserAccount;

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenUserIsAuthenticatedAndAuthorizedAndValidRequestBody_thenReturn200() throws Exception {
        UUID testUserId = UUID.randomUUID();

        this.mockMvc.perform(patch("api/users/{requestedId}", testUserId)
                .with(user(testUserAccount)));
    }

}
