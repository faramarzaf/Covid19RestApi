package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ApplicationEntity>> getAllEntities() {
        List<ApplicationEntity> allStats = applicationService.getAllStats();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/alldb")
    public ResponseEntity<List<ApplicationEntity>> getAlldb() {
        List<ApplicationEntity> allStats = applicationService.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/alldb/{id}")
    public ResponseEntity<ApplicationEntity> getEntityById(@PathVariable("id") Long id) {
        ApplicationEntity entity = applicationService.findEmployeeById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllEntities() {
        applicationService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
