package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecoveredRepository extends JpaRepository<RecoveredEntity, Long> {
    Optional<RecoveredEntity> findEntityById(Long id);
}
