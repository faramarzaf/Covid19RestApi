package com.faramarz.spring.Covid19RestApi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ApplicationEntity implements Serializable {

    @Id
    private Long id;

    private String provinceState;
    private String countryRegion;
    private String lat;
    private String lon;
    private int latestTotalCases;
    private int diffFromPrevDay;


    public ApplicationEntity() {
    }

    public ApplicationEntity(Long id, String provinceState, String countryRegion, String lat, String lon, int latestTotalCases, int diffFromPrevDay) {
        this.id = id;
        this.provinceState = provinceState;
        this.countryRegion = countryRegion;
        this.lat = lat;
        this.lon = lon;
        this.latestTotalCases = latestTotalCases;
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getProvinceState() {
        return provinceState;
    }

    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                ", provinceState='" + provinceState + '\'' +
                ", countryRegion='" + countryRegion + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}