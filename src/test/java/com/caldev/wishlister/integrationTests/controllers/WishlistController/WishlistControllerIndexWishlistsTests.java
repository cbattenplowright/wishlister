package com.caldev.wishlister.integrationTests.controllers.WishlistController;

import com.caldev.wishlister.controllers.WishlistController;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = WishlistController.class)
public class WishlistControllerIndexWishlistsTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserAccount userAccount;

    @MockBean
    WishlistService wishlistService;

    @MockBean
    List<Wishlist> wishlistList;

    @BeforeEach
    void setUp() {

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndRequestingAllWishlists() throws Exception {

        List<Wishlist> wishlistList = new ArrayList<>(List.of(new Wishlist("wishlist1", userAccount, new ArrayList<>()), new Wishlist("wishlist2", userAccount, new ArrayList<>())));

        when(wishlistService.findAllWishlists()).thenReturn(wishlistList);

        this.mockMvc.perform(get("/api/wishlists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401_whenUnauthenticatedAndRequestingAllWishlists() throws Exception {

        this.mockMvc.perform(get("/api/wishlists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldReturn403_whenUnauthorizedAndRequestingAllWishlists() throws Exception {

        this.mockMvc.perform(get("/api/wishlists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

}
