package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.Constants;
import com.faramarz.spring.Covid19RestApi.model.DeadEntity;
import com.faramarz.spring.Covid19RestApi.model.GlobalDeadEntity;
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

public abstract class ServiceDeadAbstractionLayer {

    public void prepareCSVRequestDeadOperation() throws IOException, InterruptedException {
        List<DeadEntity> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.URL_DEAD)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        fillEntityProperties(records, newStats);
        getTotalStatistics(newStats);
    }


    public void fetchDeadData() throws IOException, InterruptedException {
        prepareCSVRequestDeadOperation();
    }

    public void getTotalStatistics(List<DeadEntity> newEntity){
        GlobalDeadEntity globalDeadEntity = new GlobalDeadEntity();
        int totalDeadToday = newEntity.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        int totalReportedDead = newEntity.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        for (long j = 0; j <= newEntity.size(); j++)
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
