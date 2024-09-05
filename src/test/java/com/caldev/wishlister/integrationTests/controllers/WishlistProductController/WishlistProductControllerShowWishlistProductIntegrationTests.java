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
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistProductController.class)
public class WishlistProductControllerShowWishlistProductIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistProductService wishlistProductService;

    UUID testUserId;

    UserAccount testUserAccount;

    Long testWishlistProductId;

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
                new HashSet<>(List.of(new Authority("ROLE_USER")))
        );

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

        testWishlistProductId = 1L;

        testWishlistProduct = new WishlistProduct(
                testWishlist,
                testProduct,
                false
        );

    }

    @Test
    public void shouldReturn200_whenWishlistProductExists() throws Exception {

        when(wishlistProductService.getWishlistProductById(testWishlistProductId)).thenReturn(testWishlistProduct);

        this.mockMvc.perform(get("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", testUserId, testWishlistProductId)
                .with(user(testUserAccount)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401_whenUnauthenticated() throws Exception {

        this.mockMvc.perform(get("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", testUserId, testWishlistProductId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_whenNotAuthorized() throws Exception {
        this.mockMvc.perform(get("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", randomUUID(), testWishlistProductId)
                        .with(user(testUserAccount)))
                .andExpect(status().isForbidden());
    }
}
