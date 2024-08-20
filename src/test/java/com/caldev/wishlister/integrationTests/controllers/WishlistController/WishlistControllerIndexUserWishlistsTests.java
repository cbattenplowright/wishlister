package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.entities.Wishlist;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import com.caldev.wishlister.services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerIndexUserWishlistsTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

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
    void shouldReturn200_WhenAuthenticatedAndAuthorizedAndRequestingAllUserWishlists() throws Exception {

        List<Wishlist> wishlistList = new ArrayList<>(List.of(new Wishlist("wishlist1", testUserAccount, new ArrayList<>()), new Wishlist("wishlist2", testUserAccount, new ArrayList<>())));

        when(wishlistService.findAllUserWishlists(any(UUID.class))).thenReturn(wishlistList);

        this.mockMvc.perform(get("/api/wishlists/{requestedId}", testUserId)
                .with(user(testUserAccount)))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturn401_WhenUnauthenticatedAndRequestingAllUserWishlists() throws Exception {

        this.mockMvc.perform(get("/api/wishlists/{requestedId}", testUserId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_WhenUnauthorizedAndRequestingAllUserWishlists() throws Exception {

        this.mockMvc.perform(get("/api/wishlists/{requestedId}", UUID.randomUUID())
            .with(user(testUserAccount)))
            .andExpect(status().isForbidden());
    }
}
