package com.caldev.wishlister.integrationTests.controllers;


import com.caldev.wishlister.controllers.UserController;
import com.caldev.wishlister.entities.UserAccount;
import com.caldev.wishlister.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
public class UserAccountControllerUnitTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @MockBean
    UserAccount userAccount;

    @Test
    @WithMockUser // Annotation simulates the user logging in
    void whenValidRequestAndUsersExist_thenReturn200() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userAccount));

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(userAccount.toString()));
    }
}
