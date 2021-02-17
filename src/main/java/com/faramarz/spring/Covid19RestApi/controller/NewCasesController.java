package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceNewCases;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "Set of endpoints for retrieving and deleting of NewCasesEntity.")
public class NewCasesController {

    @Autowired
    private ServiceNewCases serviceNewCases;

    @ApiOperation("Returns list of new cases (reported/confirmed) statistic")
    @GetMapping("/all/new_case")
    public ResponseEntity<List<NewCasesEntity>> getAllNewCases() {
        List<NewCasesEntity> allStats = serviceNewCases.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @ApiOperation("Returns new cases statistic by country id")
    @GetMapping("/new_case/{id}")
    public ResponseEntity<NewCasesEntity> getNewCasesById(@PathVariable("id") Long id) {
        NewCasesEntity entity = serviceNewCases.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns new cases statistic by country name")
    @GetMapping("/new_case")
    @ResponseBody
    public ResponseEntity<List<NewCasesEntity>> getNewCaseByCountryRegion(@RequestParam(value = "country") String countryRegion) {
        List<NewCasesEntity> entity = serviceNewCases.findEntityByCountryRegionIgnoreCase(countryRegion);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns new cases statistic by latitude and longitude")
    @GetMapping("/new_case/coordinate/")
    @ResponseBody
    public ResponseEntity<NewCasesEntity> getNewCasesEntityByLatAndLon(@RequestParam(value = "lat") String lat, @RequestParam(value = "long") String lon) {
        NewCasesEntity entity = serviceNewCases.findNewCasesEntityByLatAndLon(lat, lon);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns list of global new cases statistic")
    @GetMapping("/global/new_case")
    public ResponseEntity<List<GlobalNewCaseEntity>> getGlobalDead() {
        List<GlobalNewCaseEntity> allStats = serviceNewCases.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }


}
