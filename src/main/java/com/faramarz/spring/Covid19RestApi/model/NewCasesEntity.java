package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonPropertyOrder({"id", "lat", "lon", "provinceState", "countryRegion", "latestTotalCases", "diffFromPrevDay"})
@ApiModel(description = "Class representing a NewCasesEntity by the application.")
public class NewCasesEntity implements Serializable {

    @Id
    private Long id;

    @ApiModelProperty(notes = "Name of the province", example = "British Columbia", required = true, position = 1)
    private String provinceState;

    @ApiModelProperty(notes = "Name of the country", example = "Canada", required = true, position = 2)
    private String countryRegion;

    @ApiModelProperty(notes = "Latitude of the region", example = "-65.00184", required = true, position = 3)
    private String lat;

    @ApiModelProperty(notes = "Longitude of the region", example = "45.64684", required = true, position = 4)
    private String lon;

    @ApiModelProperty(notes = "Total new cases until today in this specific province/country", example = "1200", required = true, position = 5)
    private int latestTotalCases;

    @ApiModelProperty(notes = "The number of new cases since yesterday", example = "25", required = true, position = 6)
    private int diffFromPrevDay;


    public NewCasesEntity() {
    }

    public NewCasesEntity(Long id, String provinceState, String countryRegion, String lat, String lon, int latestTotalCases, int diffFromPrevDay) {
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

    @JsonProperty("long")
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "NewCasesEntity{" +
                ", provinceState='" + provinceState + '\'' +
                ", countryRegion='" + countryRegion + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}