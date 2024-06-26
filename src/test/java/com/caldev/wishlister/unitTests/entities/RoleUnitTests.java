package com.caldev.wishlister.unitTests.entities;

import com.caldev.wishlister.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoleUnitTests {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role("ROLE_ADMIN");
    }

    @Test
    public void shouldGetIdTest(){
        assertNull(role.getId());
    }

    @Test
    public void shouldGetRoleNameTest(){
        assertThat(role.getRoleName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void shouldSetRoleNameTest(){
        role.setRoleName("ROLE_USER");
        assertThat(role.getRoleName()).isEqualTo("ROLE_USER");
    }

    @Test
    public void shouldToStringTest(){
        assertThat(role.toString()).isEqualTo("Role{id=null, roleName='ROLE_ADMIN', users=null}");
    }
}
