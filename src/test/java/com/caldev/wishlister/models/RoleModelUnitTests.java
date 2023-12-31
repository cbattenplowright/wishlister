package com.caldev.wishlister.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.enums.RoleName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleModelUnitTests {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(RoleName.ROLE_ADMIN);
    }

    @Test
    void testGettersAndSetters() {

        // Test getters
        assertEquals(null, role.getRoleId());
        assertEquals(RoleName.ROLE_ADMIN, role.getRole());
        assertEquals(null, role.getUsers());

        // Test setters

        role.setRoleId(1L);
        assertEquals(1L, role.getRoleId());

        role.setRole(RoleName.ROLE_USER);
        assertEquals(RoleName.ROLE_USER, role.getRole());

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity("Bill23", "password123", "Bill", "bill@gmail.com", LocalDate.of(1934, 5, 7)));
        userEntities.add(new UserEntity("Sally45", "password123", "Sally", "sally@gmail.com", LocalDate.of(1945, 3, 12)));
        role.setUsers(userEntities);
        assertEquals(userEntities, role.getUsers());
    }

    @Test
    void testToString() {
        role.setRoleId(1L);
        role.setRole(RoleName.ROLE_ADMIN);
        String expected = "Role{roleId=1, roleName=ROLE_ADMIN}";
        assertEquals(expected, role.toString());
    }
}