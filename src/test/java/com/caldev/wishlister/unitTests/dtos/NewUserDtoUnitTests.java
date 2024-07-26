package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.NewUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class NewUserDtoUnitTests {

    private NewUserDto newUserDto;

    @BeforeEach
    void setUp(){
        newUserDto = new NewUserDto("newUser@email.com", "password", "name", LocalDate.of(2022, 1, 1));
    }

    @Test
    void shouldGetEmail(){
        String email = newUserDto.getEmail();
        assertThat(email).isEqualTo("newUser@email.com");
    }

    @Test
    void shouldGetPassword(){
        String password = newUserDto.getPassword();
        assertThat(password).isEqualTo("password");
    }

    @Test
    void shouldGetName() {
        String name = newUserDto.getName();
        assertThat(name).isEqualTo("name");
    }

    @Test
    void shouldGetDateOfBirth() {
        LocalDate dateOfBirth = newUserDto.getDateOfBirth();
        assertThat(dateOfBirth).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    void shouldSetEmail(){
        newUserDto.setEmail("newEmail@email.com");
        assertThat(newUserDto.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    void shouldSetPassword(){
        newUserDto.setPassword("newPassword");
        assertThat(newUserDto.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void shouldSetName() {
        newUserDto.setName("newName");
        assertThat(newUserDto.getName()).isEqualTo("newName");
    }

    @Test
    void shouldSetDateOfBirth() {
        newUserDto.setDateOfBirth(LocalDate.of(2021, 1, 1));
        assertThat(newUserDto.getDateOfBirth()).isEqualTo(LocalDate.of(2021, 1, 1));
    }

    @Test
    void shouldToString() {
        String toString = newUserDto.toString();
        assertThat(toString).isEqualTo("NewUserDto{email='newUser@email.com', password='password', name='name', dateOfBirth=2022-01-01}");
    }
}
