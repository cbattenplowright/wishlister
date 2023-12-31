package com.caldev.wishlister.unitTests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.models.User;

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
    void testGetRoleId() {
        role.setRoleId(1L);
        assertEquals(1L, role.getRoleId());
    }

    @Test
    void testGetRoleName() {
        assertEquals(RoleName.ROLE_ADMIN, role.getRole());
    }

    @Test
    void testSetRoleName() {
        role.setRole(RoleName.ROLE_USER);
        assertEquals(RoleName.ROLE_USER, role.getRole());
    }

    @Test
    void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Bill23", "password123", "Bill", "bill@gmail.com", LocalDate.of(1934, 5, 7), null));
        users.add(new User("Sally45", "password123", "Sally", "sally@gmail.com", LocalDate.of(1945, 3, 12), null));
        role.setUsers(users);
        assertEquals(users, role.getUsers());
    }

    @Test
    void testToString() {
        role.setRoleId(1L);
        role.setRole(RoleName.ROLE_ADMIN);
        String expected = "Role{roleId=1, roleName=ROLE_ADMIN}";
        assertEquals(expected, role.toString());
    }
}