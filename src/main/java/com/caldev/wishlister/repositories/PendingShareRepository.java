package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.PendingShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingShareRepository extends JpaRepository<PendingShare, Long> {

    PendingShare findByToken(String token);
    List<PendingShare> findAllByRecipientEmail(String recipientEmail);
}
