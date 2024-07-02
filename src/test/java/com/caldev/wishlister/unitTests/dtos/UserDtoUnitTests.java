package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoUnitTests {

    private UserDto userDto;

    @BeforeEach
    public void setUp(){
        userDto = new UserDto("username", "password", "name", "email", LocalDate.of(2022, 1, 1), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void shouldGetUsername() {
        String username = userDto.getUsername();
        assertThat(username).isEqualTo("username");
    }

    @Test
    public void shouldGetPassword() {
        String password = userDto.getPassword();
        assertThat(password).isEqualTo("password");
    }

    @Test
    public void shouldGetName() {
        String name = userDto.getName();
        assertThat(name).isEqualTo("name");
    }

    @Test
    public void shouldGetEmail() {
        String email = userDto.getEmail();
        assertThat(email).isEqualTo("email");
    }

    @Test
    public void shouldGetDateOfBirth() {
        LocalDate dateOfBirth = userDto.getDateOfBirth();
        assertThat(dateOfBirth).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldGetRoleIds() {
        ArrayList<Long> roleIds = userDto.getRoleIds();
        assertThat(roleIds).isEmpty();
    }

    @Test
    public void shouldGetWishlistIds() {
        ArrayList<Long> wishlistIds = userDto.getWishlistIds();
        assertThat(wishlistIds).isEmpty();
    }

    @Test
    public void shouldGetProductIds() {
        ArrayList<Long> productIds = userDto.getProductIds();
        assertThat(productIds).isEmpty();
    }

    @Test
    public void shouldSetUsername() {
        userDto.setUsername("newUsername");
        assertThat(userDto.getUsername()).isEqualTo("newUsername");
    }

    @Test
    public void shouldSetPassword(){
        userDto.setPassword("newPassword");
        assertThat(userDto.getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void shouldSetName(){
        userDto.setName("newName");
        assertThat(userDto.getName()).isEqualTo("newName");
    }

    @Test
    public void shouldSetEmail(){
        userDto.setEmail("newEmail@email.com");
        assertThat(userDto.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    public void shouldSetDateOfBirth(){
        userDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertThat(userDto.getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void shouldSetRoleIds(){
        ArrayList<Long> roleIds = new ArrayList<Long>();
        roleIds.add(1L);
        roleIds.add(2L);
        userDto.setRoleIds(roleIds);
        assertThat(userDto.getRoleIds()).isEqualTo(roleIds);
    }

    @Test
    public void shouldSetWishlistIds(){
        ArrayList<Long> wishlistIds = new ArrayList<Long>();
        wishlistIds.add(1L);
        wishlistIds.add(2L);
        userDto.setWishlistIds(wishlistIds);
        assertThat(userDto.getWishlistIds()).isEqualTo(wishlistIds);
    }

    @Test
    public void shouldSetProductIds(){
        ArrayList<Long> productIds = new ArrayList<Long>();
        productIds.add(1L);
        productIds.add(2L);
        userDto.setProductIds(productIds);
        assertThat(userDto.getProductIds()).isEqualTo(productIds);
    }

    @Test
    public void shouldToString() {
        String toString = userDto.toString();
        assertThat(toString).isEqualTo("UserDto{username='username', password='password', name='name', email='email', dateOfBirth=2022-01-01, roleIds=[], wishlistIds=[], productIds=[]}");
    }
}
