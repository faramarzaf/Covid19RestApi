package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceRecovered;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Api(description = "Set of endpoints for retrieving and deleting of RecoveredEntity.")
public class RecoveredController {

    @Autowired
    private ServiceRecovered serviceRecovered;

    @ApiOperation("Returns list of recovered statistic")
    @GetMapping("/all/recovered")
    public ResponseEntity<List<RecoveredEntity>> getAllRecovered() {
        List<RecoveredEntity> allStats = serviceRecovered.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @ApiOperation("Returns list of recovered statistic by country id")
    @GetMapping("/recovered/{id}")
    public ResponseEntity<RecoveredEntity> getRecoveredById(@PathVariable("id") Long id) {
        RecoveredEntity entity = serviceRecovered.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns list of recovered statistic by country name")
    @GetMapping("/recovered")
    @ResponseBody
    public ResponseEntity<List<RecoveredEntity>> getRecoveredByCountryRegion(@RequestParam(value = "country") String countryRegion) {
        List<RecoveredEntity> entity = serviceRecovered.findEntityByCountryRegionIgnoreCase(countryRegion);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns list of recovered statistic by latitude and longitude")
    @GetMapping("/recovered/coordinate/")
    @ResponseBody
    public ResponseEntity<RecoveredEntity> getRecoveredEntityByLatAndLon(@RequestParam(value = "lat") String lat, @RequestParam(value = "long") String lon) {
        RecoveredEntity entity = serviceRecovered.findRecoveredEntityByLatAndLon(lat, lon);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns list of global recovered statistic")
    @GetMapping("/global/recovered")
    public ResponseEntity<List<GlobalRecoveredEntity>> getGlobalDead() {
        List<GlobalRecoveredEntity> allStats = serviceRecovered.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @DeleteMapping("/delete/recovered/all")
    public ResponseEntity<?> deleteAllRecovered() {
        serviceRecovered.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/recovered/global")
    public ResponseEntity<?> deleteGlobalDead() {
        serviceRecovered.deleteAllGlobal();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
