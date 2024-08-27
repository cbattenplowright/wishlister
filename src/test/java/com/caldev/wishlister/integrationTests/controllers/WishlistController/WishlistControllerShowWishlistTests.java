package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.WishlistService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerShowWishlistTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistService wishlistService;

    UserAccount testUserAccount;

    Wishlist testWishlist;

    UUID testUserId;

    Long testWishlistId;

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

        testWishlistId = 1L;

        testWishlist = new Wishlist("testWishlist", testUserAccount, new ArrayList<>());

        testWishlist.setId(testWishlistId);
    }


    @Test
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndRequestingWishlist() throws Exception {

        when(wishlistService.findWishlistById(any(Long.class))).thenReturn(Optional.ofNullable(testWishlist));

        this.mockMvc.perform(get("/api/wishlists/{requestedUserId}/{requestedWishlistId}", testUserId, testWishlistId)
                .with(user(testUserAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(testWishlist.toString()));
    }

    @Test
    void shouldReturn401_whenUnauthenticatedAndRequestingWishlist() throws Exception {

        this.mockMvc.perform(get("/api/wishlists/{requestedUserId}/{requestedWishlistId}", testUserId, testWishlistId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_whenUnauthorizedAndRequestingWishlist() throws Exception {

        this.mockMvc.perform(get("/api/wishlists/{requestedUserId}/{requestedWishlistId}", randomUUID(), testWishlistId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn404_whenAuthenticatedAndAuthorizedAndNoWishlist() throws Exception {

        when(wishlistService.findWishlistById(any(Long.class))).thenReturn(null);

        this.mockMvc.perform(get("/api/wishlists/{requestedUserId}/{requestedWishlistId}", testUserId, testWishlistId)
                        .with(user(testUserAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}
