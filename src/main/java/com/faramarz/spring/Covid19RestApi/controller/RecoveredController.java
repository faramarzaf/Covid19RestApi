package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceRecovered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class RecoveredController {

    @Autowired
    private ServiceRecovered serviceRecovered;

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

    @DeleteMapping("/delete/recovered/all")
    public ResponseEntity<?> deleteAllRecovered() {
        serviceRecovered.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/global/recovered")
    public ResponseEntity<List<GlobalRecoveredEntity>> getGlobalDead() {
        List<GlobalRecoveredEntity> allStats = serviceRecovered.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @DeleteMapping("/delete/recovered/global")
    public ResponseEntity<?> deleteGlobalDead() {
        serviceRecovered.deleteAllGlobal();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
