package com.caldev.wishlister.unitTests.dtos;

import com.caldev.wishlister.dtos.UserAccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserAccountDtoUnitTests {

    private UserAccountDto userAccountDto;

    @BeforeEach
    public void setUp(){
        userAccountDto = new UserAccountDto("user@email.com", "password", "name", LocalDate.of(2022, 1, 1), null, null, null);
    }


    @Test
    public void shouldGetPassword() {
        String password = userAccountDto.getPassword();
        assertThat(password).isEqualTo("password");
    }

    @Test
    public void shouldGetName() {
        String name = userAccountDto.getName();
        assertThat(name).isEqualTo("name");
    }

    @Test
    public void shouldGetEmail() {
        String email = userAccountDto.getEmail();
        assertThat(email).isEqualTo("user@email.com");
    }

    @Test
    public void shouldGetDateOfBirth() {
        LocalDate dateOfBirth = userAccountDto.getDateOfBirth();
        assertThat(dateOfBirth).isEqualTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void shouldGetAuthorityIds() {
        ArrayList<Long> roleIds = userAccountDto.getAuthorityIds();
        assertNull(roleIds);
    }

    @Test
    public void shouldGetWishlistIds() {
        ArrayList<Long> wishlistIds = userAccountDto.getWishlistIds();
        assertNull(wishlistIds);
    }

    @Test
    public void shouldGetProductIds() {
        ArrayList<Long> productIds = userAccountDto.getProductIds();
        assertNull(productIds);
    }


    @Test
    public void shouldSetPassword(){
        userAccountDto.setPassword("newPassword");
        assertThat(userAccountDto.getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void shouldSetName(){
        userAccountDto.setName("newName");
        assertThat(userAccountDto.getName()).isEqualTo("newName");
    }

    @Test
    public void shouldSetEmail(){
        userAccountDto.setEmail("newEmail@email.com");
        assertThat(userAccountDto.getEmail()).isEqualTo("newEmail@email.com");
    }

    @Test
    public void shouldSetDateOfBirth(){
        userAccountDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertThat(userAccountDto.getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void shouldSetAuthorityIds(){
        ArrayList<Long> authorityIds = new ArrayList<Long>();
        authorityIds.add(1L);
        authorityIds.add(2L);
        userAccountDto.setAuthorityIds(authorityIds);
        assertThat(userAccountDto.getAuthorityIds()).isEqualTo(authorityIds);
    }

    @Test
    public void shouldSetWishlistIds(){
        ArrayList<Long> wishlistIds = new ArrayList<Long>();
        wishlistIds.add(1L);
        wishlistIds.add(2L);
        userAccountDto.setWishlistIds(wishlistIds);
        assertThat(userAccountDto.getWishlistIds()).isEqualTo(wishlistIds);
    }

    @Test
    public void shouldSetProductIds(){
        ArrayList<Long> productIds = new ArrayList<Long>();
        productIds.add(1L);
        productIds.add(2L);
        userAccountDto.setProductIds(productIds);
        assertThat(userAccountDto.getProductIds()).isEqualTo(productIds);
    }

    @Test
    public void shouldToString() {
        String toString = userAccountDto.toString();
        assertThat(toString).isEqualTo("UserDto{email='user@email.com', password='password', name='name', dateOfBirth=2022-01-01, authorityIds=null, wishlistIds=null, productIds=null}");
    }
}
