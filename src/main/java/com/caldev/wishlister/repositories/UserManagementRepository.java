package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserManagementRepository extends JpaRepository<UserAccount, UUID> {

    UserAccount findByUsername(String username);
}
