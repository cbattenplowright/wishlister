package com.caldev.wishlister.repositories;

import com.caldev.wishlister.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.DatabaseType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = DatabaseType.POSTGRES)
class RoleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindByName() {
        // Add your test logic here
        // For example:
        assertEquals(RoleName.ROLE_ADMIN, roleRepository.findByRoleName(RoleName.valueOf("ROLE_ADMIN")).get().getRole());
    }

}
