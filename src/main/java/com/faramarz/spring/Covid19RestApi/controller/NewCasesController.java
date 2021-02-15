package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceNewCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NewCasesController {

    //TODO make swagger doc


    @Autowired
    private ServiceNewCases serviceNewCases;

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


    @DeleteMapping("/delete/new_case/all")
    public ResponseEntity<?> deleteAllNewCases() {
        serviceNewCases.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/global/new_case")
    public ResponseEntity<List<GlobalNewCaseEntity>> getGlobalDead() {
        List<GlobalNewCaseEntity> allStats = serviceNewCases.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @DeleteMapping("/delete/new_case/global")
    public ResponseEntity<?> deleteGlobalDead() {
        serviceNewCases.deleteAllGlobal();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
