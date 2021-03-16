package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalStatisticEntity;
import com.faramarz.spring.Covid19RestApi.other.CSVHttpRequestHelper;
import com.faramarz.spring.Covid19RestApi.other.Constants;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceAbstractionLayer {

    private static List<ApplicationEntity> newStats = new ArrayList<>();

    public void fetchData() throws IOException, InterruptedException {
        fillNewCasesProperties();
        fillDeadProperties();
        fillRecoveredProperties();
        getTotalDeadStatistics();
    }

    private void getTotalDeadStatistics() {
        GlobalStatisticEntity globalStatisticEntity = new GlobalStatisticEntity();

        // int totalDeadToday = newEntity.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        int totalDeadToday = newStats.stream().mapToInt(ApplicationEntity::getDiffDeadFromPrevDay).sum();
        int totalReportedDead = newStats.stream().mapToInt(ApplicationEntity::getLatestTotalDead).sum();

        int totalRecoveredToday = newStats.stream().mapToInt(ApplicationEntity::getDiffRecoveredFromPrevDay).sum();
        int totalReportedRecovered = newStats.stream().mapToInt(ApplicationEntity::getLatestTotalRecovered).sum();

        int totalNewCaseToday = newStats.stream().mapToInt(ApplicationEntity::getDiffNewCasesFromPrevDay).sum();
        int totalReportedNewCase = newStats.stream().mapToInt(ApplicationEntity::getLatestTotalNewCases).sum();

        for (long j = 0; j <= newStats.size(); j++)
            globalStatisticEntity.setId(j);

        globalStatisticEntity.setTotalDeadToday(totalDeadToday);
        globalStatisticEntity.setTotalReportedDead(totalReportedDead);


        globalStatisticEntity.setTotalNewCasesToday(totalNewCaseToday);
        globalStatisticEntity.setTotalReportedNewCases(totalReportedNewCase);

        globalStatisticEntity.setTotalRecoveredToday(totalRecoveredToday);
        globalStatisticEntity.setTotalReportedRecovered(totalReportedRecovered);


        saveGlobalInDB(globalStatisticEntity);
    }


    private void fillNewCasesProperties() throws IOException, InterruptedException {
        for (CSVRecord record : getCsvConfirmed()) {
            ApplicationEntity locationStats = new ApplicationEntity();

            setPropertyId(newStats, locationStats);
            setCommonAttributes(locationStats, record);
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalNewCases(latestCases);
            locationStats.setDiffNewCasesFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStats);
            savePropertiesInDB(locationStats);
        }
    }

    private void fillRecoveredProperties() throws IOException, InterruptedException {
        for (CSVRecord record : getCsvRecovered()) {
            ApplicationEntity locationStats = new ApplicationEntity();
            setPropertyId(newStats, locationStats);
            setCommonAttributes(locationStats, record);

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            locationStats.setLatestTotalRecovered(latestCases);
            locationStats.setDiffRecoveredFromPrevDay(latestCases - prevDayCases);

            newStats.add(locationStats);
            savePropertiesInDB(locationStats);
        }
    }

    private void fillDeadProperties() throws IOException, InterruptedException {
        for (CSVRecord record : getCsvDead()) {
            ApplicationEntity locationStats = new ApplicationEntity();
            setPropertyId(newStats, locationStats);
            setCommonAttributes(locationStats, record);
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalDead(latestCases);
            locationStats.setDiffDeadFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStats);
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

    private void setCommonAttributes(ApplicationEntity locationStats, CSVRecord record) {
        locationStats.setProvinceState(record.get("Province/State"));
        locationStats.setCountryRegion(record.get("Country/Region"));
        locationStats.setLat(record.get("Lat"));
        locationStats.setLon(record.get("Long"));
    }

    public abstract void savePropertiesInDB(ApplicationEntity locationStats);

    public abstract void saveGlobalInDB(GlobalStatisticEntity globalStatisticEntity);

}