package com.mambunveasna.student_management_api.repository;

import com.mambunveasna.student_management_api.model.RefreshToken;
import com.mambunveasna.student_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
    @Transactional
    void deleteByUser(User user);
}
