package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.UserAccount;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface UserRepository extends Repository<UserAccount, UUID> {
    UserAccount findByUsername(String username);
}
