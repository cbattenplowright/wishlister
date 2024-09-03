package com.caldev.wishlister.integrationTests.controllers.WishlistProductController;

import com.caldev.wishlister.controllers.WishlistProductController;
import com.caldev.wishlister.entities.*;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.WishlistProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistProductController.class)
public class WishlistProductControllerINDEXWishlistProductIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistProductService wishlistProductService;

    UUID testUserId;

    UserAccount testUserAccount;

    Wishlist testWishlist;

    Product testProduct;

    WishlistProduct testWishlistProduct;

    @BeforeEach
    void setUp() throws Exception {
        testUserId = randomUUID();

        testUserAccount = new UserAccount(
                "testUser@gmail.com",
                "password",
                "testName",
                LocalDate.of(1999, 8, 10),
                new HashSet<>(List.of(new Authority("ROLE_USER"))));

        testUserAccount.setId(testUserId);

        testWishlist = new Wishlist(
                "testWishlist",
                testUserAccount,
                null
        );

        testProduct = new Product(
                "testProduct",
                156,
                new URL("https://www.testUrl.com"),
                new URL("https://www.imageUrl.com"),
                "testDescription",
                PrioritySelection.IMPORTANT,
                LocalDate.now(),
                new ArrayList<>(),
                testUserAccount
        );

        testWishlistProduct = new WishlistProduct(
                testWishlist,
                testProduct,
                false
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndIndexingWishlistProduct() throws Exception {
        when (wishlistProductService.getAllWishlistProducts()).thenReturn(List.of(testWishlistProduct));

        this.mockMvc.perform(get("/api/wishlist-products"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401_whenNotAuthenticated() throws Exception {
        this.mockMvc.perform(get("/api/wishlist-products"))
                .andExpect(status().isUnauthorized());
    }
}