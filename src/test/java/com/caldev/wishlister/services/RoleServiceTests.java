package com.caldev.wishlister.services;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;
import com.caldev.wishlister.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    // TODO implement findByRoleName test

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void testFindByRoleName() {
        // Arrange
        // test to find a role by its name
        RoleName roleName = RoleName.ROLE_USER;
        Role role = new Role(RoleName.ROLE_USER);

        // Simulate the role being found
        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(role));

        // Act
        Role foundRole = roleService.findByRoleName(roleName);

        // Assert
        assertThat(foundRole).isEqualTo(role);

        Mockito.verify(roleRepository, Mockito.times(1)).findByRoleName(roleName);
    }
}
