package com.elbio.centraldeajuda.repository;

import com.elbio.centraldeajuda.entities.Call;
import com.elbio.centraldeajuda.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    Page<Call> findByUser(User user, Pageable pageable);
    List<Call> findBySubjectContainingOrId(String subject, Long id);
}
