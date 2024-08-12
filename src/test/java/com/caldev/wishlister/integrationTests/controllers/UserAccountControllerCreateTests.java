package com.caldev.wishlister.integrationTests.controllers;

import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.security.CustomUserDetailsService;
import com.caldev.wishlister.security.SecurityConfig;
import com.caldev.wishlister.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerCreateTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserService userService;

    @MockBean
    UserAccount userAccount;

    @Test
    void whenUnauthorizedAndCreatingUser_thenReturn403() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        UserAccount unauthorizedUser = new UserAccount("unauth@email.com", "password", "unauth", LocalDate.of(2000, 1, 1), null);

        this.mockMvc.perform(post("/api/users/new")
                        .with(user(unauthorizedUser))
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserAccount("user@email.com", "password", "user", LocalDate.of(2000, 1, 1), null))))
                .andExpect(status().isForbidden());
    }
}
