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
    ApplicationEntity locationStats = new ApplicationEntity();

    private void prepareCSVRequestOperation() throws IOException, InterruptedException {
        List<ApplicationEntity> newStats = new ArrayList<>();

        Iterable<CSVRecord> csvDeadRecords = CSVHttpRequestHelper.request(Constants.URL_DEAD);
        Iterable<CSVRecord> csvConfirmedRecords = CSVHttpRequestHelper.request(Constants.URL_CONFIRMED);
        Iterable<CSVRecord> csvRecoveredRecords = CSVHttpRequestHelper.request(Constants.URL_RECOVERED);


        //  fillProperties(csvDeadRecords, csvRecoveredRecords, csvConfirmedRecords, newStats);

        fillNewCasesProperties(csvConfirmedRecords,newStats);
        fillDeadProperties(csvDeadRecords,newStats);
        fillRecoveredProperties(csvRecoveredRecords,newStats);

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

/*    private void fillProperties(Iterable<CSVRecord> csvDeadRecords, Iterable<CSVRecord> csvRecoveredRecords, Iterable<CSVRecord> csvNewCasesRecords, List<ApplicationEntity> newEntity) {
        ApplicationEntity locationStats = new ApplicationEntity();

        //dead
        for (CSVRecord deadRecord : csvDeadRecords) {


           *//* for (long j = 0; j <= newEntity.size(); j++)
                locationStats.setId(j);*//*

            locationStats.setProvinceState(deadRecord.get("Province/State"));
            locationStats.setCountryRegion(deadRecord.get("Country/Region"));
            locationStats.setLat(deadRecord.get("Lat"));
            locationStats.setLon(deadRecord.get("Long"));
            int latestCases = Integer.parseInt(deadRecord.get(deadRecord.size() - 1));
            int prevDayCases = Integer.parseInt(deadRecord.get(deadRecord.size() - 2));
            locationStats.setLatestTotalDead(latestCases);
            locationStats.setDiffDeadFromPrevDay(latestCases - prevDayCases);
            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }

        //recovered
        for (CSVRecord recoveredRecord : csvRecoveredRecords) {

            *//*for (long j = 0; j <= newEntity.size(); j++)
                locationStats.setId(j);*//*
            locationStats.setProvinceState(recoveredRecord.get("Province/State"));
            locationStats.setCountryRegion(recoveredRecord.get("Country/Region"));
            locationStats.setLat(recoveredRecord.get("Lat"));
            locationStats.setLon(recoveredRecord.get("Long"));

            int latestRecoveredCases = Integer.parseInt(recoveredRecord.get(recoveredRecord.size() - 1));
            int prevDayRecovered = Integer.parseInt(recoveredRecord.get(recoveredRecord.size() - 2));
            locationStats.setLatestTotalRecovered(latestRecoveredCases);
            locationStats.setDiffRecoveredFromPrevDay(latestRecoveredCases - prevDayRecovered);
            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }

        //new case
        for (CSVRecord newCaseRecord : csvNewCasesRecords) {

           *//* for (long j = 0; j <= newEntity.size(); j++)
                locationStats.setId(j);*//*
            locationStats.setProvinceState(newCaseRecord.get("Province/State"));
            locationStats.setCountryRegion(newCaseRecord.get("Country/Region"));
            locationStats.setLat(newCaseRecord.get("Lat"));
            locationStats.setLon(newCaseRecord.get("Long"));

            int latestNewCases = Integer.parseInt(newCaseRecord.get(newCaseRecord.size() - 1));
            int prevDayNewCases = Integer.parseInt(newCaseRecord.get(newCaseRecord.size() - 2));
            locationStats.setLatestTotalNewCases(latestNewCases);
            locationStats.setDiffNewCasesFromPrevDay(latestNewCases - prevDayNewCases);
            newEntity.add(locationStats);
            savePropertiesInDB(locationStats);
        }

    }*/

    private void fillRecoveredProperties(Iterable<CSVRecord> newRecord, List<ApplicationEntity> newEntity) {
        for (CSVRecord record : newRecord) {

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

    private void fillNewCasesProperties(Iterable<CSVRecord> newRecord, List<ApplicationEntity> newEntity) {
        for (CSVRecord record : newRecord) {

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

    private void fillDeadProperties(Iterable<CSVRecord> newRecord, List<ApplicationEntity> newEntity) {
        for (CSVRecord record : newRecord) {

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

    public abstract void savePropertiesInDB(ApplicationEntity locationStats);

    public abstract void saveGlobalInDB(GlobalStatisticEntity globalStatisticEntity);

}
