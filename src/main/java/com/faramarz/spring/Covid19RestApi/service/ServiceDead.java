package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.exception.ApiRequestException;
import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.repository.DeadRepository;
import com.faramarz.spring.Covid19RestApi.service.abstraction.ServiceDeadAbstractionLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service("ServiceDead")
public class ServiceDead extends ServiceDeadAbstractionLayer {

    private final DeadRepository deadRepository;

    @Autowired
    public ServiceDead(DeadRepository deadRepository) {
        this.deadRepository = deadRepository;
    }

    public List<DeadEntity> getEntities() {
        return deadRepository.findAll();
    }

    public void deleteAll() {
        deadRepository.deleteAll();
    }

    public DeadEntity findEntityById(Long id) {
        return deadRepository.findEntityById(id).orElseThrow(() -> new ApiRequestException("Case by id " + id + " was not found!"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    /*@Scheduled(cron = "0 0 0/1 * * *")*/
    @Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    @Override
    public void fetchDeadData() throws IOException, InterruptedException {
        super.fetchDeadData();
    }

    @Override
    public void saveDeadInDB(DeadEntity locationStats) {
        deadRepository.save(locationStats);
    }


}
