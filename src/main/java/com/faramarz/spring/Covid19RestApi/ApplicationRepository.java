package com.faramarz.spring.Covid19RestApi;


import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    Optional<ApplicationEntity> findEmployeeById(Long id);
}