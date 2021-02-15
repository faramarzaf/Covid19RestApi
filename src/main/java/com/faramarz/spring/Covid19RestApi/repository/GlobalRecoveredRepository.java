package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalRecoveredRepository extends JpaRepository<GlobalRecoveredEntity, Long> {

}
