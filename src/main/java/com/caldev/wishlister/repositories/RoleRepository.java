package com.caldev.wishlister.repositories;

import com.caldev.wishlister.enums.RoleName;
import com.caldev.wishlister.models.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);
}
