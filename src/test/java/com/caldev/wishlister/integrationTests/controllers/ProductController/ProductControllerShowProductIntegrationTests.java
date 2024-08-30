package com.caldev.wishlister.integrationTests.controllers.ProductController;

import com.caldev.wishlister.controllers.ProductController;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.PrioritySelection;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerShowProductIntegrationTests {

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

    Long testWishlistId;

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

        testWishlistId = 1L;

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
    void shouldReturn200_whenAuthenticatedAndAuthorizedAndRequestingProduct() throws Exception {
        when(productService.findProductById(testProductId)).thenReturn(Optional.ofNullable(testProduct));

        this.mockMvc.perform(get("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(testProduct.toString()));
    }
}
