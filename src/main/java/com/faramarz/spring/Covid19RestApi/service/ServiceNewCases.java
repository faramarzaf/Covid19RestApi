package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.repository.NewCasesRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceNewCasesAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service("ServiceNewCases")
public class ServiceNewCases extends ServiceNewCasesAbstractionLayer {

    private final NewCasesRepository newCasesRepository;

    @Autowired
    public ServiceNewCases(NewCasesRepository newCasesRepository) {
        this.newCasesRepository = newCasesRepository;
    }

    public List<NewCasesEntity> getEntities() {
        return newCasesRepository.findAll();
    }

    public void deleteAll() {
        newCasesRepository.deleteAll();
    }

    public NewCasesEntity findEntityById(Long id) {
        return newCasesRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    /*@Scheduled(cron = "0 0 0/1 * * *")*/
    @Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    @Override
    public void fetchNewCasesData() throws IOException, InterruptedException {
        super.fetchNewCasesData();
    }

    @Override
    public void saveNewCasesInDB(NewCasesEntity locationStats) {
        newCasesRepository.save(locationStats);
    }

}
