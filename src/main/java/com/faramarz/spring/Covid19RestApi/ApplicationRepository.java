package com.faramarz.spring.Covid19RestApi;


import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
}