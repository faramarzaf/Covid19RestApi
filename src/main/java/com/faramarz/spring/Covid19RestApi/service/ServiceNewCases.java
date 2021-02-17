package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.other.RunDataBaseOperationInThread;
import com.faramarz.spring.Covid19RestApi.repository.GlobalNewCasesRepository;
import com.faramarz.spring.Covid19RestApi.repository.NewCasesRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceNewCasesAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("ServiceNewCases")
public class ServiceNewCases extends ServiceNewCasesAbstractionLayer {

    private final NewCasesRepository newCasesRepository;
    private final GlobalNewCasesRepository globalNewCasesRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public ServiceNewCases(NewCasesRepository newCasesRepository, GlobalNewCasesRepository globalNewCasesRepository) {
        this.newCasesRepository = newCasesRepository;
        this.globalNewCasesRepository = globalNewCasesRepository;
    }

    public List<NewCasesEntity> getEntities() {
        return newCasesRepository.findAll();
    }

    public List<GlobalNewCaseEntity> getGlobalEntities() {
        return globalNewCasesRepository.findAll();
    }

    public void deleteAll() {
        newCasesRepository.deleteAll();
    }

    public void deleteAllGlobal() {
        globalNewCasesRepository.deleteAll();
    }

    public NewCasesEntity findEntityById(Long id) {
        return newCasesRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    public NewCasesEntity findNewCasesEntityByLatAndLon(String lat, String lon) {
        return newCasesRepository.findNewCasesEntityByLatAndLon(lat, lon).orElseThrow(() -> new ApiRequestException("Case by lat " + lat + " lon " + lon + " was not found!"));
    }

    public List<NewCasesEntity> findEntityByCountryRegionIgnoreCase(String countryRegion) {
        return newCasesRepository.findEntityByCountryRegionIgnoreCase(countryRegion).orElseThrow(() -> new ApiRequestException("Case by countryRegion " + countryRegion + " was not found!"));
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @PostConstruct
    @Override
    public void fetchNewCasesData() throws IOException, InterruptedException {
        super.fetchNewCasesData();
    }

    @Override
    public void saveNewCasesInDB(NewCasesEntity locationStats) {
        RunDataBaseOperationInThread.build().execute(() -> newCasesRepository.save(locationStats));
    }

    @Override
    public void saveGlobalNewCaseInDB(GlobalNewCaseEntity globalNewCaseEntity) {
        RunDataBaseOperationInThread.build().execute(() -> globalNewCasesRepository.save(globalNewCaseEntity));
    }

}
