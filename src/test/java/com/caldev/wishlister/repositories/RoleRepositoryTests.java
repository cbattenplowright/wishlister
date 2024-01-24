package com.caldev.wishlister.repositories;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {

        Role role = new Role(RoleName.ROLE_USER);

        Optional<Role> foundRole = roleRepository.findByRoleName(RoleName.ROLE_USER);
        assertTrue(foundRole.isPresent());
        assertEquals(RoleName.ROLE_USER, foundRole.get().getRole());
    }

}
