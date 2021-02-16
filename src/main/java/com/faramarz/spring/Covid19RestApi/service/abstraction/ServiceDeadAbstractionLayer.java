package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
import com.faramarz.spring.Covid19RestApi.other.CSVHttpRequestHelper;
import com.faramarz.spring.Covid19RestApi.other.Constants;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceDeadAbstractionLayer {

    private void prepareCSVRequestDeadOperation() throws IOException, InterruptedException {
        List<DeadEntity> newStats = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = CSVHttpRequestHelper.request(Constants.URL_DEAD);
        fillEntityProperties(csvRecords, newStats);
        getTotalStatistics(newStats);
    }


    public void fetchDeadData() throws IOException, InterruptedException {
        prepareCSVRequestDeadOperation();
    }

    private void getTotalStatistics(List<DeadEntity> deadEntities) {
        GlobalDeadEntity globalDeadEntity = new GlobalDeadEntity();
        int totalDeadToday = deadEntities.stream().mapToInt(DeadEntity::getDiffFromPrevDay).sum();
        int totalReportedDead = deadEntities.stream().mapToInt(DeadEntity::getLatestTotalCases).sum();
        for (long j = 0; j <= deadEntities.size(); j++)
            globalDeadEntity.setId(j);
        globalDeadEntity.setTotalDeadToday(totalDeadToday);
        globalDeadEntity.setTotalReportedDead(totalReportedDead);
        saveGlobalDeadInDB(globalDeadEntity);
    }

    private void fillEntityProperties(Iterable<CSVRecord> newRecord, List<DeadEntity> newEntity) {
        for (CSVRecord record : newRecord) {
            DeadEntity locationStats = new DeadEntity();
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
            saveDeadInDB(locationStats);
        }
    }

    public abstract void saveDeadInDB(DeadEntity locationStats);

    public abstract void saveGlobalDeadInDB(GlobalDeadEntity globalDeadEntity);

}
