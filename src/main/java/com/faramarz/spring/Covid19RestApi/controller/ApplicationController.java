package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


}
