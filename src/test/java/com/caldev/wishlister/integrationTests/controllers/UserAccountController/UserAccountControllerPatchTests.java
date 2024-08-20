package com.caldev.wishlister.integrationTests.controllers.UserAccountController;

import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.dtos.UserAccountDto;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.AuthorityRepository;
import com.caldev.wishlister.repositories.ProductRepository;
import com.caldev.wishlister.repositories.UserManagementRepository;
import com.caldev.wishlister.repositories.WishlistRepository;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerPatchTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    UserService userService;
    UUID testUserId;
    UserAccount testUserAccount;
    String validJsonRequest;

    @BeforeEach
    void setUp() {
        testUserId = randomUUID();

        testUserAccount = new UserAccount(
                "testUser@gmail.com",
                "password",
                "testName",
                LocalDate.of(1999, 8, 10),
                new HashSet<>(List.of(new Authority("ROLE_USER"))));

        testUserAccount.setId(testUserId);

        validJsonRequest = """
                {
                "email": "email@email.com",
                "password": "password3",
                "name": "name3",
                "dateOfBirth": "1999-08-19",
                "authorityIds": [1],
                "wishlistIds": null,
                "productIds": null
                }
                """;

        UserAccountDto testUserAccountDto = new UserAccountDto("testUser@gmail.com", "password", "testName", LocalDate.of(1999, 8, 10), new ArrayList<>(), null, null);

    }

    @Test
    void shouldReturn200WhenRequestBodyIsValid() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        ArrayList<Long> authorityIds = new ArrayList<>();
        authorityIds.add(2L);

        UserAccountDto updatedUserInfo = new UserAccountDto("updatedTestUser@email.com", "updatedPassword", "updatedTestUserName", LocalDate.of(2000, 1, 1), authorityIds, null, null);

        String jsonRequest = objectMapper.writeValueAsString(updatedUserInfo);

        when(userService.updateUser(any(UUID.class), any(UserAccountDto.class))).thenReturn(testUserAccount);
//        doReturn(testUserAccount).when(userService).updateUser(testUserId, updateUser);

        this.mockMvc.perform(patch("/api/users/{requestedId}", testUserId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenRequestBodyIsInvalid() throws Exception {

        String jsonRequest = """
                {
                "email": "email",
                "password": "password3",
                "name": "name3",
                "dateOfBirth": "1999-08-19",
                "authorityIds": [1],
                "wishlistIds": null,
                "productIds": null
                }
                """;

        this.mockMvc.perform(patch("/api/users/{requestedId}", testUserId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401WhenUserIsNotAuthenticated() throws Exception {

        this.mockMvc.perform(patch("/api/users/{requestedId}", testUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403WhenUserIsNotAuthorized() throws Exception {

        testUserAccount.setId(randomUUID());

        this.mockMvc.perform(patch("/api/users/{requestedId}", testUserId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn404WhenUserIsNotFound() throws Exception {

        UUID invalidUserId = randomUUID();
        when(userService.getUserById(invalidUserId)).thenReturn(null);

        testUserAccount.setAuthorities(new HashSet<>(List.of(new Authority("ROLE_ADMIN"))));

        this.mockMvc.perform(patch("/api/users/{requestedId}", invalidUserId)
                .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJsonRequest))
                .andExpect(status().isNotFound());
    }
}
