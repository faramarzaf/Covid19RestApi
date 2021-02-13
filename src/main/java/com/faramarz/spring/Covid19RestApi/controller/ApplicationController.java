package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

/*    @PostMapping("/addAll")
    public ResponseEntity<?> addAll(@RequestBody List<ApplicationEntity> entities) {
        List<ApplicationEntity> allStats = applicationService.addEntities(entities);
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }*/

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationEntity>> getAllEntities() {
        List<ApplicationEntity> allStats = applicationService.getAllStats();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

/*    @GetMapping("/alldatabase")
    public ResponseEntity<List<ApplicationEntity>> getAllEntitiess() {
        List<ApplicationEntity> allStats = applicationService.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }*/

 /*   @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllEmployees() {
        applicationService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
