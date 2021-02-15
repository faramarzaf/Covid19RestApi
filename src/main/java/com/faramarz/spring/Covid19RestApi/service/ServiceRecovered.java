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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service("ServiceRecovered")
public class ServiceRecovered extends ServiceRecoveredAbstractionLayer {

    private final RecoveredRepository recoveredRepository;
    private final GlobalRecoveredRepository globalRecoveredRepository;

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

    public void deleteAll() {
        recoveredRepository.deleteAll();
    }

    public void deleteAllGlobal() {
        globalRecoveredRepository.deleteAll();
    }

    public RecoveredEntity findEntityById(Long id) {
        return recoveredRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    /*@Scheduled(cron = "0 0 0/1 * * *")*/
    @Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    @Override
    public void fetchRecoveredData() throws IOException, InterruptedException {
        super.fetchRecoveredData();
    }

    @Override
    public void saveRecoveredInDB(RecoveredEntity locationStats) {
        recoveredRepository.save(locationStats);
    }

    @Override
    public void saveGlobalNewCaseInDB(GlobalRecoveredEntity locationStats) {
        globalRecoveredRepository.save(locationStats);
    }

}
