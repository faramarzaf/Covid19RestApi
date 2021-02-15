package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NewCasesRepository extends JpaRepository<NewCasesEntity, Long> {

    Optional<NewCasesEntity> findEntityById(Long id);

    Optional<NewCasesEntity> findNewCasesEntityByLatAndLon(String lat, String lon);

    Optional<List<NewCasesEntity>> findNewCasesEntitiesByCountryRegion(String countryRegion);

}