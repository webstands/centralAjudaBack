package com.elbio.centraldeajuda.repository;

import com.elbio.centraldeajuda.entities.Call;
import com.elbio.centraldeajuda.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    List<Call> findByUser(User user);
    List<Call> findBySubjectContainingOrId(String subject, Long id);
    List<Call> findByUser_UserId(UUID userId);
}
