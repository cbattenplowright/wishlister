package com.caldev.wishlister.integrationTests.controllers;

import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerShowTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

    @MockBean
    UserAccount userAccount;

    @BeforeEach
    void setUp(){
        when(userService.getUserById(UUID.randomUUID())).thenReturn(userAccount);
    }

    @Test
    @WithMockUser
    void whenUnauthorizedAndRequestingUser_thenReturn403() throws Exception {

        this.mockMvc.perform(get("/api/users/{id}", UUID.randomUUID())
                        .with(user(userAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    void whenUnauthenticatedAndRequestingUser_thenReturn401() throws Exception {

        this.mockMvc.perform(get("/api/users/{id}", UUID.randomUUID())
                        .with(user("admin").password("").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthenticatedAndAuthorizedAndRequestingUser_thenReturn200() throws Exception {

        UserAccount testUserAccount = new UserAccount("user@email.com","password", "user", LocalDate.of(2022, 10, 10), new HashSet<>(List.of(new Authority("ROLE_USER"))));
        testUserAccount.setId(UUID.randomUUID());
        when(userService.getUserById(testUserAccount.getId())).thenReturn(testUserAccount);

        this.mockMvc.perform(get("/api/users/{id}", testUserAccount.getId())
                        .with(user(testUserAccount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
