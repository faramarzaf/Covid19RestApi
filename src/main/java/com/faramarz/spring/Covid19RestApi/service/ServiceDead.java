package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
import com.faramarz.spring.Covid19RestApi.repository.DeadRepository;
import com.faramarz.spring.Covid19RestApi.repository.GlobalDeadRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceDeadAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("ServiceDead")
public class ServiceDead extends ServiceDeadAbstractionLayer {

    private final DeadRepository deadRepository;
    private final GlobalDeadRepository globalDeadRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Autowired
    public ServiceDead(DeadRepository deadRepository, GlobalDeadRepository globalDeadRepository) {
        this.deadRepository = deadRepository;
        this.globalDeadRepository = globalDeadRepository;
    }

    public List<DeadEntity> getEntities() {
        return deadRepository.findAll();
    }

    public List<GlobalDeadEntity> getGlobalEntities() {
        return globalDeadRepository.findAll();
    }

    public DeadEntity findEntityById(Long id) {
        return deadRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    public List<DeadEntity> findEntityByCountry(String countryRegion) {
        return deadRepository.findEntityByCountryRegionIgnoreCase(countryRegion).orElseThrow(() -> new ApiRequestException("Case by countryRegion " + countryRegion + " was not found!"));
    }

    public DeadEntity findDeadEntityByLatAndLon(String lat, String lon) {
        return deadRepository.findDeadEntityByLatAndLon(lat, lon).orElseThrow(() -> new ApiRequestException("Case by lat " + lat + " lon " + lon + " was not found!"));
    }


    @Scheduled(cron = "0 0 0/1 * * *")
    @PostConstruct
    @Override
    public void fetchDeadData() throws IOException, InterruptedException {
        super.fetchDeadData();
    }

    @Override
    public void saveDeadInDB(DeadEntity locationStats) {
        //deadRepository.save(locationStats);
        executorService.execute(() -> deadRepository.save(locationStats));
    }

    @Override
    public void saveGlobalDeadInDB(GlobalDeadEntity globalDeadEntity) {
        globalDeadRepository.save(globalDeadEntity);
    }

}
