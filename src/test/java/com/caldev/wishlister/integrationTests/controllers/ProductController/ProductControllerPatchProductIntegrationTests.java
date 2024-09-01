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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

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
                new URL("testUrl"),
                new URL("testImageUrl"),
                PrioritySelection.IMPORTANT,
                "testDescription",
                LocalDate.now(),
                new ArrayList<>()
        );

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        validJsonRequest = objectMapper.writeValueAsString(testProductDto);
    }
}
