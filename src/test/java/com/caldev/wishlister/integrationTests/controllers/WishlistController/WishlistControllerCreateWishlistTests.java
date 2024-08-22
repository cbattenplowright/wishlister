package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerCreateWishlistTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customerUserDetailsService;

    @MockBean
    WishlistService wishlistService;

    UserAccount testUserAccount;

    UUID testUserId;

    @BeforeEach
    void setUp(){
        testUserId = randomUUID();

        testUserAccount = new UserAccount(
                "testUser@gmail.com",
                "password",
                "testName",
                LocalDate.of(1999, 8, 10),
                new HashSet<>(List.of(new Authority("ROLE_USER"))));

        testUserAccount.setId(testUserId);
    }

    @Test
    void shouldReturn201_whenAuthenticatedAndAuthorizedAndCreatingWishlist() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        WishlistDto newWishlistDto = new WishlistDto(testUserId, "newWishlistName");

        String jsonRequest = objectMapper.writeValueAsString();

        this.mockMvc.perform(post("/api/wishlists/new")
                .cont)
    }
}