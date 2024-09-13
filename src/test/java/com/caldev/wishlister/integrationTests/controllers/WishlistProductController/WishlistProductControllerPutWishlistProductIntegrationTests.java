package com.caldev.wishlister.integrationTests.controllers.WishlistProductController;

import com.caldev.wishlister.controllers.WishlistProductController;
import com.caldev.wishlister.dtos.WishlistProductDto;
import com.caldev.wishlister.entities.*;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.WishlistProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers=WishlistProductController.class)
public class WishlistProductControllerPutWishlistProductIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistProductService wishlistProductService;

    UUID testUserId;

    UserAccount testUserAccount;

    Wishlist testWishlist;

    Long testWishlistProductId;

    WishlistProductDto testWishlistProductDto;

    Product testProduct;

    WishlistProduct testWishlistProduct;

    ObjectMapper objectMapper;

    String validJsonRequest;


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

        testWishlistProduct.setWishlistProductId(1L);

        testWishlistProductDto = new WishlistProductDto(
                1L,
                23L,
                true
        );

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        validJsonRequest = objectMapper.writeValueAsString(testWishlistProductDto);
    }

    @Test
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndUpdatingWishlistProduct() throws Exception {
        when(wishlistProductService.existsByWishlistIdAndProductId(any(Long.class), any(Long.class))).thenReturn(true);

        when(wishlistProductService.updateWishlistProduct(any(Long.class), any(WishlistProductDto.class), any(UserAccount.class))).thenReturn(testWishlistProduct);

        this.mockMvc.perform(put("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", testUserId, testWishlistProduct.getWishlistProductId())
                        .with(user(testUserAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400_whenRequestIsInvalid() throws Exception {
        String invalidJsonRequest = """
                {
                    "wishlistProductId": null
                }
                """;

        when(wishlistProductService.existsByWishlistIdAndProductId(any(Long.class), any(Long.class))).thenReturn(true);

        when(wishlistProductService.updateWishlistProduct(any(Long.class), any(WishlistProductDto.class), any(UserAccount.class))).thenReturn(testWishlistProduct);

        this.mockMvc.perform(put("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", testUserId, testWishlistProduct.getWishlistProductId())
                        .with(user(testUserAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401_whenUnauthenticatedAndUpdatingWishlistProduct() throws Exception {
        this.mockMvc.perform(put("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", testUserId, testWishlistProduct.getWishlistProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_whenUnauthorizedAndUpdatingWishlistProduct() throws Exception {

        this.mockMvc.perform(put("/api/wishlist-products/{requestedUserId}/{requestedWishlistProductId}", randomUUID(), testWishlistProduct.getWishlistProductId())
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isForbidden());
    }



}
