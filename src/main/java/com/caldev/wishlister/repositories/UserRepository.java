package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.UserAccount;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<UserAccount, UUID> {

    Optional<UserAccount> findByEmail(String email);
}
