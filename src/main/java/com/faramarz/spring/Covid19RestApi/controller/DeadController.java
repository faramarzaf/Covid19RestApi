package com.faramarz.spring.Covid19RestApi.controller;

import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
import com.faramarz.spring.Covid19RestApi.service.ServiceDead;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(description = "Set of endpoints for retrieving and deleting of DeadEntity.")
public class DeadController {

    @Autowired
    private ServiceDead serviceDead;


    @ApiOperation("Returns list of dead statistic")
    @GetMapping("/all/dead")
    public ResponseEntity<List<DeadEntity>> getAllDead() {
        List<DeadEntity> allStats = serviceDead.getEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @ApiOperation("Returns list of global dead statistic")
    @GetMapping("/global/dead")
    public ResponseEntity<List<GlobalDeadEntity>> getGlobalDead() {
        List<GlobalDeadEntity> allStats = serviceDead.getGlobalEntities();
        return new ResponseEntity<>(allStats, HttpStatus.OK);
    }

    @ApiOperation("Returns dead statistic by country id")
    @GetMapping("/dead/{id}")
    public ResponseEntity<DeadEntity> getDeadById(@PathVariable("id") Long id) {
        DeadEntity entity = serviceDead.findEntityById(id);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns dead statistic by country name")
    @GetMapping("/dead")
    @ResponseBody
    public ResponseEntity<List<DeadEntity>> getDeadByCountryRegion(@RequestParam(value = "country") String countryRegion) {
        List<DeadEntity> entity = serviceDead.findEntityByCountryRegionIgnoreCase(countryRegion);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation("Returns dead statistic by latitude and longitude")
    @GetMapping("/dead/coordinate/")
    @ResponseBody
    public ResponseEntity<DeadEntity> getDeadByLatAndLon(@RequestParam(value = "lat") String lat, @RequestParam(value = "long") String lon) {
        DeadEntity entity = serviceDead.findDeadEntityByLatAndLon(lat, lon);
        return new ResponseEntity<>(entity, HttpStatus.OK);
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
