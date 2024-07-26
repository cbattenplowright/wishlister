package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.Authority;
import com.caldev.wishlister.entities.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserAccountUnitTests {

    private UserAccount userAccount;

    private Authority admin;

    @BeforeEach
    public void setUp() {
        admin = new Authority("ROLE_USER");
        userAccount = new UserAccount("john@email.com", "password", "name", LocalDate.of(2022, 1, 1), null);
    }

    @Test
    public void shouldGetIdTest(){
        assertThat(userAccount.getId()).isNull();
    }

    @Test
    public void shouldGetPasswordTest(){
        assertThat(userAccount.getPassword()).isEqualTo("password");
    }

    @Test
    public void shouldGetNameTest(){
        assertThat(userAccount.getName()).isEqualTo("name");
    }

    @Test
    public void shouldGetEmailTest(){
        assertThat(userAccount.getEmail()).isEqualTo("john@email.com");
        assertThat(userAccount.getEmail()).contains("@");
    }

    @Test
    public void shouldGetDateOfBirthTest(){
        assertThat(userAccount.getDateOfBirth()).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldSetEmailTest(){
        userAccount.setEmail("newEmail@email.com");
        assertThat(userAccount.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    public void shouldSetPasswordTest(){
        userAccount.setPassword("newPassword");
        assertThat(userAccount.getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void shouldSetNameTest(){
        userAccount.setName("newName");
        assertThat(userAccount.getName()).isEqualTo("newName");
    }

    @Test
    public void shouldSetDateOfBirthTest(){
        userAccount.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertThat(userAccount.getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void shouldGetAuthoritiesTest(){
        assertThat(userAccount.getAuthorities()).isEqualTo(null);
    }

    @Test
    public void shouldSetAuthoritiesTest(){
        userAccount.setAuthorities(null);
        assertNull(userAccount.getAuthorities());
    }

    @Test
    public void shouldGetWishlistsTest(){
        assertNull(userAccount.getWishlists());
    }

    @Test
    public void shouldSetWishlistsTest(){
        userAccount.setWishlists(null);
        assertNull(userAccount.getWishlists());
    }

    @Test
    public void shouldGetProductsTest(){
        assertNull(userAccount.getProducts());
    }

    @Test
    public void shouldSetProductsTest(){
        userAccount.setProducts(null);
        assertNull(userAccount.getProducts());
    }

    @Test
    public void shouldToStringTest(){
        assertThat(userAccount.toString()).isEqualTo("UserAccount{id=null, email='john@email.com', password='password', name='name', dateOfBirth=2022-01-01, authorities=null, wishlists=null, products=null}");
    }
}
