package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.Constants;
import com.faramarz.spring.Covid19RestApi.model.GlobalNewCaseEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalRecoveredEntity;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
import com.faramarz.spring.Covid19RestApi.model.RecoveredEntity;
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

public abstract class ServiceRecoveredAbstractionLayer {

    public void prepareCSVRequestRecoveredOperation() throws IOException, InterruptedException {
        List<RecoveredEntity> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.URL_RECOVERED)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        fillEntityProperties(records, newStats);
        getTotalStatistics(newStats);
    }


    public void fetchRecoveredData() throws IOException, InterruptedException {
        prepareCSVRequestRecoveredOperation();
    }

    public void getTotalStatistics(List<RecoveredEntity> newEntity) {
        GlobalRecoveredEntity globalRecoveredEntity = new GlobalRecoveredEntity();
        int totalNewCaseToday = newEntity.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        int totalReportedNewCase = newEntity.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        for (long j = 0; j <= newEntity.size(); j++)
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
