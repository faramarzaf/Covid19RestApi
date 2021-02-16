package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
import com.faramarz.spring.Covid19RestApi.other.CSVHttpRequestHelper;
import com.faramarz.spring.Covid19RestApi.other.Constants;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceRecoveredAbstractionLayer {

    public void prepareCSVRequestRecoveredOperation() throws IOException, InterruptedException {
        List<RecoveredEntity> newStats = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = CSVHttpRequestHelper.request(Constants.URL_RECOVERED);
        fillEntityProperties(csvRecords, newStats);
        getTotalStatistics(newStats);
    }


    public void fetchRecoveredData() throws IOException, InterruptedException {
        prepareCSVRequestRecoveredOperation();
    }

    public void getTotalStatistics(List<RecoveredEntity> recoveredEntities) {
        GlobalRecoveredEntity globalRecoveredEntity = new GlobalRecoveredEntity();
        int totalNewCaseToday = recoveredEntities.stream().mapToInt(RecoveredEntity::getDiffFromPrevDay).sum();
        int totalReportedNewCase = recoveredEntities.stream().mapToInt(RecoveredEntity::getLatestTotalCases).sum();
        for (long j = 0; j <= recoveredEntities.size(); j++)
            globalRecoveredEntity.setId(j);
        globalRecoveredEntity.setTotalRecoveredToday(totalNewCaseToday);
        globalRecoveredEntity.setTotalReportedRecovered(totalReportedNewCase);
        saveGlobalNewCaseInDB(globalRecoveredEntity);
    }

    private void fillEntityProperties(Iterable<CSVRecord> newRecord, List<RecoveredEntity> newEntity) {
        for (CSVRecord record : newRecord) {
            RecoveredEntity locationStats = new RecoveredEntity();
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
            saveRecoveredInDB(locationStats);
        }
    }

    public abstract void saveRecoveredInDB(RecoveredEntity locationStats);

    public abstract void saveGlobalNewCaseInDB(GlobalRecoveredEntity locationStats);

}
