package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
import com.caldev.wishlister.dtos.WishlistDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerPatchWishlistTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistService wishlistService;

    UUID testUserId;

    UserAccount testUserAccount;

    Wishlist testWishlist;

    WishlistDto testWishlistDto;

    String validJsonRequest;

    @BeforeEach
    void setUp() {

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
                new ArrayList<>()
        );

        testWishlistDto = new WishlistDto(
                testUserId,
                "testWishlist"
        );
    }

    @Test
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndUpdatingWishlist() throws Exception {

        when(wishlistService.updateWishlist(any(Long.class), any(WishlistDto.class), any(UserAccount.class))).thenReturn(testWishlist);

        this.mockMvc.perform(patch("/api/wishlists/{requestedId}", testWishlist.getId())
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isOk());
    }
}
