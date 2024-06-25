package com.caldev.wishlister.unitTests;

import com.caldev.wishlister.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserUnitTests {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("username", "password", "name", "email@email.com", LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldGetIdTest(){
        assertThat(user.getId()).isNull();
    }

    @Test
    public void shouldGetUsernameTest(){
        assertThat(user.getUsername()).isEqualTo("username");
    }

    @Test
    public void shouldGetPasswordTest(){
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void shouldGetNameTest(){
        assertThat(user.getName()).isEqualTo("name");
    }

    @Test
    public void shouldGetEmailTest(){
        assertThat(user.getEmail()).isEqualTo("email@email.com");
        assertThat(user.getEmail()).contains("@");
    }

    @Test
    public void shouldGetDateOfBirthTest(){
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldSetUsernameTest(){
        user.setUsername("newUsername");
        assertThat(user.getUsername()).isEqualTo("newUsername");
    }

    @Test
    public void shouldSetPasswordTest(){
        user.setPassword("newPassword");
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void shouldSetNameTest(){
        user.setName("newName");
        assertThat(user.getName()).isEqualTo("newName");
    }

    @Test
    public void shouldSetEmailTest(){
        user.setEmail("newEmail@email.com");
        assertThat(user.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    public void shouldSetDateOfBirthTest(){
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }
}
