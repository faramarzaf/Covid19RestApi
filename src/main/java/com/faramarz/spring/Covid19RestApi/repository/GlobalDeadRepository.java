package com.faramarz.spring.Covid19RestApi.repository;


import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalDeadRepository extends JpaRepository<GlobalDeadEntity, Long> {

}
