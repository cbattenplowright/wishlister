package com.caldev.wishlister.integrationTests.controllers;


import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.repositories.UserManagementRepository;
import com.caldev.wishlister.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerUnitTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    UserAccount userAccount;

    @MockBean
    UserManagementRepository userRepository;



    @BeforeEach
    public void setUp() {
        userAccount = new UserAccount("username", "password", "name", "email@email.com", LocalDate.of(2022, 1, 1),new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        userAccount.setId(UUID.randomUUID());
        userRepository.save(userAccount);
    }


//   INDEX User Endpoint Test
    @Test
    @WithMockUser // Annotation simulates the user logging in
    void whenValidRequestAndUsersExist_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(userAccount.toString()));
    }

//    SHOW User Endpoint Test
    @Test
    @WithMockUser
    void whenValidRequestAndUserExists_thenReturn200() throws Exception {

        when(userService.getUserById(userAccount.getId())).thenReturn(userAccount);

        mockMvc.perform(get("/api/users/{requestedId}", userAccount.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(userAccount.toString()));
    }
}
