package com.bs23.repository;

import com.bs23.domain.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    @Modifying
    @Query("DELETE FROM UserToken ut WHERE ut.userId = :userId")
    void deleteCustomerToken(@Param("userId") Long userId);
}
