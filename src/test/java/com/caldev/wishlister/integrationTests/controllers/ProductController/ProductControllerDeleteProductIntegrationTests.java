package com.caldev.wishlister.integrationTests.controllers.ProductController;

import com.caldev.wishlister.controllers.ProductController;
import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.*;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.ProductService;
import com.caldev.wishlister.services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
import java.net.URL;
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
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerDeleteProductIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    ProductService productService;

    UUID testUserId;

    UserAccount testUserAccount;

    Long testProductId;

    Product testProduct;

    @BeforeEach
    void setUp() throws MalformedURLException {
        testUserId = randomUUID();

        testUserAccount = new UserAccount(
                "testUser@gmail.com",
                "password",
                "testName",
                LocalDate.of(1999, 8, 10),
                new HashSet<>(List.of(new Authority("ROLE_USER"))));

        testUserAccount.setId(testUserId);

        testProductId = 1L;

        testProduct = new Product(
                "testProduct",
                1599,
                new URL("https://www.testUrl.com"),
                new URL("https://www.imageUrl.com"),
                "description",
                PrioritySelection.NON_URGENT,
                LocalDate.now(),
                new ArrayList<>(),
                testUserAccount);

        testProduct.setProductId(testProductId);
    }

    @Test
    public void shouldReturn200_whenDeleteProduct() throws Exception {

        when(productService.findProductById(testProductId)).thenReturn(Optional.of(testProduct));

        doNothing().when(productService).deleteProduct(testProductId);

        this.mockMvc.perform(delete("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn401_whenUnauthenticated() throws Exception {

        this.mockMvc.perform(delete("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn403_whenUnauthorized() throws Exception {

        this.mockMvc.perform(delete("/api/products/{requestedUserId}/{requestedProductId}", randomUUID(), testProductId)
                .with(user(testUserAccount)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn404_whenProductNotFound() throws Exception {
        when(productService.findProductById(any(Long.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount)))
                .andExpect(status().isNotFound());
    }

}
