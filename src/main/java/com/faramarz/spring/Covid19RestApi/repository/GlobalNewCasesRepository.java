package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalNewCasesRepository extends JpaRepository<GlobalNewCaseEntity, Long> {

}
