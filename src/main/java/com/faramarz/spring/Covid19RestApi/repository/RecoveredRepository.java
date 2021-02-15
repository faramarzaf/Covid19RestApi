package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RecoveredRepository extends JpaRepository<RecoveredEntity, Long> {
    Optional<RecoveredEntity> findEntityById(Long id);

    Optional<RecoveredEntity> findRecoveredEntityByLatAndLon(String lat, String lon);


    Optional<List<RecoveredEntity>> findRecoveredEntitiesByCountryRegion(String countryRegion);

}
