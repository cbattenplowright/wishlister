package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserAccountUnitTests {

    private UserAccount userAccount;

    @BeforeEach
    public void setUp() {
        userAccount = new UserAccount("username", "password", "name", "email@email.com", LocalDate.of(2022, 1, 1),null);
    }

    @Test
    public void shouldGetIdTest(){
        assertThat(userAccount.getId()).isNull();
    }

    @Test
    public void shouldGetUsernameTest(){
        assertThat(userAccount.getUsername()).isEqualTo("username");
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
        assertThat(userAccount.getEmail()).isEqualTo("email@email.com");
        assertThat(userAccount.getEmail()).contains("@");
    }

    @Test
    public void shouldGetDateOfBirthTest(){
        assertThat(userAccount.getDateOfBirth()).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldSetUsernameTest(){
        userAccount.setUsername("newUsername");
        assertThat(userAccount.getUsername()).isEqualTo("newUsername");
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
    public void shouldSetEmailTest(){
        userAccount.setEmail("newEmail@email.com");
        assertThat(userAccount.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    public void shouldSetDateOfBirthTest(){
        userAccount.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertThat(userAccount.getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void shouldGetRolesTest(){
        assertNull(userAccount.getRoles());
    }

    @Test
    public void shouldSetRolesTest(){
        userAccount.setRoles(null);
        assertNull(userAccount.getRoles());
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
        assertThat(userAccount.toString()).isEqualTo("User{id=null, username='username', password='password', name='name', email='email@email.com', dateOfBirth=2022-01-01, roles=null, wishlists=null, products=null}");
    }
}
