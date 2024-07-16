package com.elbio.centraldeajuda.repository;

import com.elbio.centraldeajuda.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    User findByUserId(UUID userId);
}
