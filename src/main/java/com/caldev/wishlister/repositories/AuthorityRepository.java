package com.caldev.wishlister.repositories;

import com.caldev.wishlister.entities.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByRoleName(String roleName);
}
