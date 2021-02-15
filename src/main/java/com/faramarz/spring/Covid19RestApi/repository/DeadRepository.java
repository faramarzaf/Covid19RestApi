package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeadRepository extends JpaRepository<DeadEntity, Long> {

    Optional<DeadEntity> findEntityById(Long id);

    Optional<DeadEntity> findDeadEntityByLatAndLon(String lat,String lon);

    Optional<List<DeadEntity>> findDeadEntitiesByCountryRegion(String countryRegion);
}
