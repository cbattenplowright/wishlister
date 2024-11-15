package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.PendingShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingShareRepository extends JpaRepository<PendingShare, Long> {

    PendingShare findByToken(String token);
}
