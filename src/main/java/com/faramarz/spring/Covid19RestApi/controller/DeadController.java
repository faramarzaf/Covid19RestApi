package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceDead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DeadController {

    @Autowired
    private ServiceDead serviceDead;


    @GetMapping("/all/dead")
    public ResponseEntity<List<DeadEntity>> getAllDead() {
        List<DeadEntity> allStats = serviceDead.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/global/dead")
    public ResponseEntity<List<GlobalDeadEntity>> getGlobalDead() {
        List<GlobalDeadEntity> allStats = serviceDead.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @GetMapping("/dead/{id}")
    public ResponseEntity<DeadEntity> getDeadById(@PathVariable("id") Long id) {
        DeadEntity entity = serviceDead.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/new_case/all")
    public ResponseEntity<?> deleteAllNewCases() {
        serviceDead.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/dead/all")
    public ResponseEntity<?> deleteAllDead() {
        serviceDead.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/dead/global")
    public ResponseEntity<?> deleteGlobalDead() {
        serviceDead.deleteAllGlobal();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
