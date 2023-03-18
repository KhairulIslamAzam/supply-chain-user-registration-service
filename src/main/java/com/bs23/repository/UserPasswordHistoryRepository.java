package com.bs23.repository;

import com.bs23.domain.entity.UserPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordHistoryRepository extends JpaRepository<UserPasswordHistory, Long> {
    UserPasswordHistory findFirstByIdOrderByChangeDateDesc(Long id);
}
