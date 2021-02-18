package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalStatisticEntity;
import com.faramarz.spring.Covid19RestApi.repository.ApplicationRepository;
import com.faramarz.spring.Covid19RestApi.repository.GlobalRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("ApplicationService")
public class ApplicationService extends ServiceAbstractionLayer {

    private final ApplicationRepository applicationRepository;
    private final GlobalRepository globalRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, GlobalRepository globalRepository) {
        this.applicationRepository = applicationRepository;
        this.globalRepository = globalRepository;
    }


    public List<ApplicationEntity> getApplicationEntities() {
        return applicationRepository.findAll();
    }


    public ApplicationEntity getApplicationEntityById(Long id) {
        return applicationRepository.findApplicationEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    public List<ApplicationEntity> getApplicationEntityByCountryRegionIgnoreCase(String countryRegion) {
        return applicationRepository.findApplicationEntityByCountryRegionIgnoreCase(countryRegion).orElseThrow(() -> new ApiRequestException("Case by countryRegion " + countryRegion + " was not found!"));
    }

    public ApplicationEntity getApplicationEntityByLatAndLon(String lat, String lon) {
        return applicationRepository.findApplicationEntityByLatAndLon(lat, lon).orElseThrow(() -> new ApiRequestException("Case by lat " + lat + " lon " + lon + " was not found!"));
    }

    public List<GlobalStatisticEntity> getGlobalEntities() {
        return globalRepository.findAll();
    }

    public void deleteGlobalRepository(){
        globalRepository.deleteAll();
    }
    public void deleteApplicationRepository(){
        applicationRepository.deleteAll();
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @PostConstruct
    @Override
    public void fetchData() throws IOException, InterruptedException {
        super.fetchData();
    }

    @Override
    public void savePropertiesInDB(ApplicationEntity locationStats) {
      executorService.execute(() -> applicationRepository.save(locationStats));
        //applicationRepository.save(locationStats);
    }

    @Override
    public void saveGlobalInDB(GlobalStatisticEntity globalStatisticEntity) {
        globalRepository.save(globalStatisticEntity);
    }
}
