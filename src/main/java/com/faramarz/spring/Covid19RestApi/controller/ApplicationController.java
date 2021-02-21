package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalStatisticEntity;
import com.faramarz.spring.Covid19RestApi.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "Set of endpoints for retrieving of ApplicationEntity.")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @ApiOperation("Returns list of statistic")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ApplicationEntity>> getAll() {
        List<ApplicationEntity> allStats = service.getApplicationEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @ApiOperation("Returns statistic by country id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApplicationEntity> getById(@PathVariable("id") Long id) {
        ApplicationEntity entity = service.getApplicationEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns statistic by country name")
    @GetMapping(value = "/global/")
    @ResponseBody
    public ResponseEntity<List<ApplicationEntity>> getByCountryRegion(@RequestParam(value = "countryName") String countryRegion) {
        List<ApplicationEntity> entity = service.getApplicationEntityByCountryRegionIgnoreCase(countryRegion);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns  statistic by latitude and longitude")
    @GetMapping(value = "/coordinate")
    @ResponseBody
    public ResponseEntity<ApplicationEntity> getDeadByLatAndLon(@RequestParam(value = "lat") String lat, @RequestParam(value = "long") String lon) {
        ApplicationEntity entity = service.getApplicationEntityByLatAndLon(lat, lon);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns list of global statistic")
    @GetMapping(value = "/global")
    public ResponseEntity<List<GlobalStatisticEntity>> getGlobal() {
        List<GlobalStatisticEntity> allStats = service.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ApplicationEntity> delete() {
        service.deleteApplicationRepository();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteall")
    public ResponseEntity<GlobalStatisticEntity> deleteGlobal() {
        service.deleteGlobalRepository();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
