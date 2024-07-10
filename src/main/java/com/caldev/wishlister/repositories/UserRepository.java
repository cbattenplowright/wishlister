package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.User;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    User findByUsername(String username);
}
