package com.faramarz.spring.Covid19RestApi.repository;

import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {


    Optional<ApplicationEntity> findApplicationEntityById(Long id);

    Optional<ApplicationEntity> findApplicationEntityByLatAndLon(String lat, String lon);

    Optional<List<ApplicationEntity>> findApplicationEntityByCountryRegionIgnoreCase(String countryRegion);


}
