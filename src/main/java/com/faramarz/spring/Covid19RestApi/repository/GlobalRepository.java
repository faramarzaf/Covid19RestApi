package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.GlobalStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalRepository extends JpaRepository<GlobalStatisticEntity, Long> {
}
