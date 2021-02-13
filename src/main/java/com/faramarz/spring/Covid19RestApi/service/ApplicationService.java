package com.faramarz.spring.Covid19RestApi.service;

import com.faramarz.spring.Covid19RestApi.Constants;
import com.faramarz.spring.Covid19RestApi.model.ApplicationEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ApplicationService extends ServiceAbstractionLayer {


    private List<ApplicationEntity> allStats = new ArrayList<>();


    @Override
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
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
            //locationStats.setId(new SecureRandom().nextLong());
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStats);
        }

        this.allStats = newStats;
    }

    public List<ApplicationEntity> getAllStats() {
        return allStats;
    }


}
