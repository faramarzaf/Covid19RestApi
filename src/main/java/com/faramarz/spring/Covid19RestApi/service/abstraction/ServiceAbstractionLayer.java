package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalStatisticEntity;
import com.faramarz.spring.Covid19RestApi.other.CSVHttpRequestHelper;
import com.faramarz.spring.Covid19RestApi.other.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceAbstractionLayer {


    private void prepareCSVRequestOperation() throws IOException, InterruptedException {
        List<ApplicationEntity> newStats = new ArrayList<>();
        fillNewCasesProperties(newStats);
        fillDeadProperties(newStats);
        fillRecoveredProperties(newStats);
        getTotalDeadStatistics(newStats);
    }

    public void fetchData() throws IOException, InterruptedException {
        prepareCSVRequestOperation();
    }

    private void getTotalDeadStatistics(List<ApplicationEntity> applicationEntities) {
        GlobalStatisticEntity globalStatisticEntity = new GlobalStatisticEntity();

        int totalDeadToday = applicationEntities.stream().mapToInt(ApplicationEntity::getDiffDeadFromPrevDay).sum();
        int totalReportedDead = applicationEntities.stream().mapToInt(ApplicationEntity::getLatestTotalDead).sum();

        int totalRecoveredToday = applicationEntities.stream().mapToInt(ApplicationEntity::getDiffRecoveredFromPrevDay).sum();
        int totalReportedRecovered = applicationEntities.stream().mapToInt(ApplicationEntity::getLatestTotalRecovered).sum();

        int totalNewCaseToday = applicationEntities.stream().mapToInt(ApplicationEntity::getDiffNewCasesFromPrevDay).sum();
        int totalReportedNewCase = applicationEntities.stream().mapToInt(ApplicationEntity::getLatestTotalNewCases).sum();

        for (long j = 0; j <= applicationEntities.size(); j++)
            globalStatisticEntity.setId(j);
        globalStatisticEntity.setTotalDeadToday(totalDeadToday);
        globalStatisticEntity.setTotalReportedDead(totalReportedDead);


        globalStatisticEntity.setTotalNewCasesToday(totalNewCaseToday);
        globalStatisticEntity.setTotalReportedNewCases(totalReportedNewCase);

        globalStatisticEntity.setTotalRecoveredToday(totalRecoveredToday);
        globalStatisticEntity.setTotalReportedRecovered(totalReportedRecovered);


        saveGlobalInDB(globalStatisticEntity);
    }




    private void fillNewCasesProperties(List<ApplicationEntity> newEntity) throws IOException, InterruptedException {
        for (CSVRecord record : getCsvConfirmed()) {
            ApplicationEntity locationStats = new ApplicationEntity();

            setPropertyId(newEntity,locationStats);
            locationStats.setProvinceState(record.get("Province/State"));
            locationStats.setCountryRegion(record.get("Country/Region"));
            locationStats.setLat(record.get("Lat"));
            locationStats.setLon(record.get("Long"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalNewCases(latestCases);
            locationStats.setDiffNewCasesFromPrevDay(latestCases - prevDayCases);
            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }
    }

    private void fillRecoveredProperties(List<ApplicationEntity> newEntity) throws IOException, InterruptedException {
        for (CSVRecord record : getCsvRecovered()) {
            ApplicationEntity locationStats = new ApplicationEntity();
            setPropertyId(newEntity,locationStats);
            locationStats.setProvinceState(record.get("Province/State"));
            locationStats.setCountryRegion(record.get("Country/Region"));
            locationStats.setLat(record.get("Lat"));
            locationStats.setLon(record.get("Long"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            locationStats.setLatestTotalRecovered(latestCases);
            locationStats.setDiffRecoveredFromPrevDay(latestCases - prevDayCases);

            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }
    }

    private void fillDeadProperties(List<ApplicationEntity> newEntity) throws IOException, InterruptedException {
        for (CSVRecord record : getCsvDead()) {
            ApplicationEntity locationStats = new ApplicationEntity();
            setPropertyId(newEntity,locationStats);
            locationStats.setProvinceState(record.get("Province/State"));
            locationStats.setCountryRegion(record.get("Country/Region"));
            locationStats.setLat(record.get("Lat"));
            locationStats.setLon(record.get("Long"));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalDead(latestCases);
            locationStats.setDiffDeadFromPrevDay(latestCases - prevDayCases);
            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }
    }



    private Iterable<CSVRecord> getCsvDead() throws IOException, InterruptedException {
        return CSVHttpRequestHelper.request(Constants.URL_DEAD);
    }

    private Iterable<CSVRecord> getCsvConfirmed() throws IOException, InterruptedException {
        return CSVHttpRequestHelper.request(Constants.URL_CONFIRMED);
    }

    private Iterable<CSVRecord> getCsvRecovered() throws IOException, InterruptedException {
        return CSVHttpRequestHelper.request(Constants.URL_RECOVERED);
    }


    private void setPropertyId(List<ApplicationEntity> newEntity, ApplicationEntity locationStats) {
        for (long j = 0; j <= newEntity.size(); j++)
            locationStats.setId(j);
    }

    public abstract void savePropertiesInDB(ApplicationEntity locationStats);

    public abstract void saveGlobalInDB(GlobalStatisticEntity globalStatisticEntity);

}
