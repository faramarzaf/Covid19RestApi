package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import com.faramarz.spring.Covid19RestApi.repository.GlobalRecoveredRepository;
import com.faramarz.spring.Covid19RestApi.repository.RecoveredRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceRecoveredAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("ServiceRecovered")
public class ServiceRecovered extends ServiceRecoveredAbstractionLayer {

    private final RecoveredRepository recoveredRepository;
    private final GlobalRecoveredRepository globalRecoveredRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Autowired
    public ServiceRecovered(RecoveredRepository recoveredRepository, GlobalRecoveredRepository globalRecoveredRepository) {
        this.recoveredRepository = recoveredRepository;
        this.globalRecoveredRepository = globalRecoveredRepository;
    }

    public List<RecoveredEntity> getEntities() {
        return recoveredRepository.findAll();
    }

    public List<GlobalRecoveredEntity> getGlobalEntities() {
        return globalRecoveredRepository.findAll();
    }

    public RecoveredEntity findEntityById(Long id) {
        return recoveredRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    public RecoveredEntity findRecoveredEntityByLatAndLon(String lat, String lon) {
        return recoveredRepository.findRecoveredEntityByLatAndLon(lat, lon).orElseThrow(() -> new ApiRequestException("Case by lat " + lat + " lon " + lon + " was not found!"));
    }

    public List<RecoveredEntity> findEntityByCountry(String countryRegion) {
        return recoveredRepository.findEntityByCountryRegionIgnoreCase(countryRegion).orElseThrow(() -> new ApiRequestException("Case by countryRegion " + countryRegion + " was not found!"));
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @PostConstruct
    @Override
    public void fetchRecoveredData() throws IOException, InterruptedException {
        super.fetchRecoveredData();
    }

    @Override
    public void saveRecoveredInDB(RecoveredEntity locationStats) {
       // recoveredRepository.save(locationStats);
        executorService.execute(() -> recoveredRepository.save(locationStats));

    }

    @Override
    public void saveGlobalNewCaseInDB(GlobalRecoveredEntity locationStats) {
        globalRecoveredRepository.save(locationStats);
    }

}
