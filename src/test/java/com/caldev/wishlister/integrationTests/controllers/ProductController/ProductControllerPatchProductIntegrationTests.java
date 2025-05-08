package com.caldev.wishlister.integrationTests.controllers.ProductController;

import com.caldev.wishlister.controllers.ProductController;
import com.caldev.wishlister.dtos.ProductDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.PrioritySelection;
import com.caldev.wishlister.entities.Product;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerPatchProductIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    ProductService productService;

    UUID testUserId;

    UserAccount testUserAccount;

    Long testProductId;

    ProductDto testProductDto;

    Product testProduct;

    ObjectMapper objectMapper;

    String validJsonRequest;

    @BeforeEach
    void setUp() throws MalformedURLException, JsonProcessingException {
        testUserId = randomUUID();

        testUserAccount = new UserAccount(
                "testUser@gmail.com",
                "password",
                "testName",
                LocalDate.of(1999, 8, 10),
                new HashSet<>(List.of(new Authority("ROLE_USER"))));

        testUserAccount.setId(testUserId);

        testProductId = 1L;

        testProductDto = new ProductDto(
                "testProduct",
                testUserId,
                1599,
                new URL("https://www.testUrl.com"),
                new URL("https://www.testImageUrl.com"),
                PrioritySelection.IMPORTANT,
                "testDescription",
                LocalDate.now(),
                new ArrayList<>()
        );

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        validJsonRequest = objectMapper.writeValueAsString(testProductDto);

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
    }

    @Test
    void shouldReturn200_whenRequestIsValid() throws Exception {

        when(productService.updateProduct(any(Long.class), any(ProductDto.class), any(UserAccount.class))).thenReturn(testProduct);

        this.mockMvc.perform(patch("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isOk());

    }

    @Test
    void shouldReturn400_whenRequestIsInvalid() throws Exception {

        String invalidJsonRequest = """
                {
                    "productName": null,
                    "sharedUserId": 1,
                    "price": 1599,
                    "url": "https://www.testUrl.com",
                    "imageUrl": "https://www.testImageUrl.com",
                    "priority": "IMPORTANT",
                    "description": null,
                    "dateAdded": "2022-01-01",
                    "wishlistProducts": []
                }
                """;

        this.mockMvc.perform(patch("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401_whenUserIsNotAuthenticated() throws Exception {

        this.mockMvc.perform(patch("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403_whenUserIsNotAuthorized() throws Exception {

        this.mockMvc.perform(patch("/api/products/{requestedUserId}/{requestedProductId}", randomUUID(), testProductId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn404_whenProductIsNotFound() throws Exception {

        when(productService.updateProduct(any(Long.class), any(ProductDto.class), any(UserAccount.class))).thenReturn(null);

        this.mockMvc.perform(patch("/api/products/{requestedUserId}/{requestedProductId}", testUserId, testProductId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isNotFound());
    }
}