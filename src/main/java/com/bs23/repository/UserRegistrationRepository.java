package com.bs23.repository;

import com.bs23.domain.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Long> {
    Optional<UserRegistration> findByUserName(String userName);
}