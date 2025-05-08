package com.grepp.smartwatcha.infra.jpa.repository;

import com.grepp.smartwatcha.infra.jpa.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
    Optional<EmailVerificationEntity> findByEmail(String email);
    Optional<EmailVerificationEntity> findByEmailAndCode(String email, String code);
} 