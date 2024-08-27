package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerDeleteUserIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    WishlistService wishlistService;

    UserAccount testUserAccount;

    Wishlist testWishlist;

    UUID testUserId;

    int testWishlistId;

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

        testWishlist = new Wishlist("testWishlist", testUserAccount, new ArrayList<>());

        testWishlistId = 1;
    }

    @Test
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndWishlistDeleted() throws Exception{

        when(wishlistService.findWishlistById(any(Long.class))).thenReturn(Optional.of(testWishlist));

        doNothing().when(wishlistService).deleteWishlist(any(Long.class));

        this.mockMvc.perform(delete("/api/wishlists/" + testUserId + "/" + testWishlistId)
                .with(user(testUserAccount)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401_whenUnauthenticated() throws Exception{


        this.mockMvc.perform(delete("/api/wishlists/" + testUserId + "/" + testWishlistId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_whenUnauthorized() throws Exception {


        this.mockMvc.perform(delete("/api/wishlists/" + randomUUID() + "/" + testWishlistId)
                        .with(user(testUserAccount)))
                .andExpect(status().isForbidden());

    }

    @Test
    void shouldReturn404_whenWishlistNotFound() throws Exception{

        when(wishlistService.findWishlistById(any(Long.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/wishlists" + testUserId + "/" + testWishlistId)
                .with(user(testUserAccount)))
                .andExpect(status().isNotFound());
    }
}
