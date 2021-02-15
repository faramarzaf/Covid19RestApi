package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceDead;
import com.faramarz.spring.Covid19RestApi.service.ServiceNewCases;
import com.faramarz.spring.Covid19RestApi.service.ServiceRecovered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    //TODO make swagger doc


    @Autowired
    private ServiceNewCases serviceNewCases;

    @Autowired
    private ServiceDead serviceDead;

    @Autowired
    private ServiceRecovered serviceRecovered;


    @GetMapping("/all/new_case")
    public ResponseEntity<List<NewCasesEntity>> getAllNewCases() {
        List<NewCasesEntity> allStats = serviceNewCases.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/new_case/{id}")
    public ResponseEntity<NewCasesEntity> getNewCasesById(@PathVariable("id") Long id) {
        NewCasesEntity entity = serviceNewCases.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    @GetMapping("/all/recovered")
    public ResponseEntity<List<RecoveredEntity>> getAllRecovered() {
        List<RecoveredEntity> allStats = serviceRecovered.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/recovered/{id}")
    public ResponseEntity<RecoveredEntity> getRecoveredById(@PathVariable("id") Long id) {
        RecoveredEntity entity = serviceRecovered.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    @GetMapping("/all/dead")
    public ResponseEntity<List<DeadEntity>> getAllDead() {
        List<DeadEntity> allStats = serviceDead.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/dead/{id}")
    public ResponseEntity<DeadEntity> getDeadById(@PathVariable("id") Long id) {
        DeadEntity entity = serviceDead.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/new_case/all")
    public ResponseEntity<?> deleteAllNewCases() {
        serviceNewCases.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/recovered/all")
    public ResponseEntity<?> deleteAllRecovered() {
        serviceRecovered.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/dead/all")
    public ResponseEntity<?> deleteAllDead() {
        serviceDead.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
