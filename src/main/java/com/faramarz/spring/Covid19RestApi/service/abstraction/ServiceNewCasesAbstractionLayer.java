package com.faramarz.spring.Covid19RestApi.service.abstraction;


import com.faramarz.spring.Covid19RestApi.Constants;
import com.faramarz.spring.Covid19RestApi.model.NewCasesEntity;
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

public abstract class ServiceNewCasesAbstractionLayer {

    public void prepareCSVRequestNewCasesOperation() throws IOException, InterruptedException {
        List<NewCasesEntity> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Constants.URL_CONFIRMED)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        fillEntityProperties(records, newStats);
    }


    public void fetchNewCasesData() throws IOException, InterruptedException {
        prepareCSVRequestNewCasesOperation();
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

}
