package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.ApplicationRepository;
import com.faramarz.spring.Covid19RestApi.Constants;
import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService extends ServiceAbstractionLayer {

    private final ApplicationRepository applicationRepository;
    private List<ApplicationEntity> allStats = new ArrayList<>();

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public List<ApplicationEntity> addEmployees(Iterable<ApplicationEntity> entities) {
        List<ApplicationEntity> result = new ArrayList<>();
        if (entities == null) {
            return result;
        }
        for (ApplicationEntity entity : entities) {
            result.add(applicationRepository.save(entity)); //TODO
        }

        return result;
    }

    public List<ApplicationEntity> getEntities() {
        return applicationRepository.findAll();
    }

    public void deleteAll() {
        applicationRepository.deleteAll();
    }

    @Override
    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Scheduled(cron = "*/10 * * * * *")
    public void fetchConfirmedData() throws IOException, InterruptedException {
        List<ApplicationEntity> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.URL_CONFIRMED)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            ApplicationEntity locationStats = new ApplicationEntity();
            locationStats.setProvinceState(record.get("Province/State"));
            locationStats.setCountryRegion(record.get("Country/Region"));
            locationStats.setLat(record.get("Lat"));
            locationStats.setLon(record.get("Long"));
            try {
                locationStats.setId(SecureRandom.getInstanceStrong().nextLong());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStats);
        }

        addEmployees(newStats);
        this.allStats = newStats;

    }

    public List<ApplicationEntity> getAllStats() {
        return allStats;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
