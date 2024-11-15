package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.Authority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthorityUnitTests {

    private Authority authority;

    @BeforeEach
    public void setUp() {
        authority = new Authority("ROLE_ADMIN");
    }

    @Test
    public void shouldGetIdTest(){
        assertNull(authority.getAuthorityId());
    }

    @Test
    public void shouldGetRoleNameTest(){
        assertThat(authority.getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void shouldSetRoleNameTest(){
        authority.setAuthority("ROLE_USER");
        assertThat(authority.getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    public void shouldToStringTest(){
        assertThat(authority.toString()).isEqualTo("Authority{id=null, authority='ROLE_ADMIN'}");
    }
}
