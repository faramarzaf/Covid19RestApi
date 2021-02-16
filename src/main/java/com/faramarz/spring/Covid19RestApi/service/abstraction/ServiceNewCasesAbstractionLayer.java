package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.other.CSVHttpRequestHelper;
import com.faramarz.spring.Covid19RestApi.other.Constants;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceNewCasesAbstractionLayer {

    public void prepareCSVRequestNewCasesOperation() throws IOException, InterruptedException {
        List<NewCasesEntity> newStats = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = CSVHttpRequestHelper.request(Constants.URL_CONFIRMED);
        fillEntityProperties(csvRecords, newStats);
        getTotalStatistics(newStats);
    }


    public void fetchNewCasesData() throws IOException, InterruptedException {
        prepareCSVRequestNewCasesOperation();
    }

    public void getTotalStatistics(List<NewCasesEntity> newCasesEntities) {
        GlobalNewCaseEntity globalNewCaseEntity = new GlobalNewCaseEntity();
        int totalNewCaseToday = newCasesEntities.stream().mapToInt(NewCasesEntity::getDiffFromPrevDay).sum();
        int totalReportedNewCase = newCasesEntities.stream().mapToInt(NewCasesEntity::getLatestTotalCases).sum();
        for (long j = 0; j <= newCasesEntities.size(); j++)
            globalNewCaseEntity.setId(j);
        globalNewCaseEntity.setTotalNewCasesToday(totalNewCaseToday);
        globalNewCaseEntity.setTotalReportedNewCases(totalReportedNewCase);
        saveGlobalNewCaseInDB(globalNewCaseEntity);
    }

    private void fillEntityProperties(Iterable<CSVRecord> newRecord, List<NewCasesEntity> newEntity) {
        for (CSVRecord record : newRecord) {
            NewCasesEntity locationStats = new NewCasesEntity();
            for (long j = 0; j <= newEntity.size(); j++)
                locationStats.setId(j);
            locationStats.setProvinceState(record.get("Province/State"));
            locationStats.setCountryRegion(record.get("Country/Region"));
            locationStats.setLat(record.get("Lat"));
            locationStats.setLon(record.get("Long"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
            newEntity.add(locationStats);
            saveNewCasesInDB(locationStats);
        }
    }

    public abstract void saveNewCasesInDB(NewCasesEntity locationStats);

    public abstract void saveGlobalNewCaseInDB(GlobalNewCaseEntity globalNewCaseEntity);

}
