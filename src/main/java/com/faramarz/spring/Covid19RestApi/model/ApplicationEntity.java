package com.faramarz.spring.Covid19RestApi.model;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonPropertyOrder({"id", "lat", "lon", "provinceState", "countryRegion", "latestTotalNewCases", "diffNewCasesFromPrevDay",
        "latestTotalDead", "diffDeadFromPrevDay", "latestTotalRecovered", "diffRecoveredFromPrevDay"
})
@ApiModel(description = "Class representing an Entity by the application.")
public class ApplicationEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private int latestTotalNewCases;

    @ApiModelProperty(notes = "The number of new cases since yesterday ", example = "25", required = true, position = 6)
    private int diffNewCasesFromPrevDay;

    @ApiModelProperty(notes = "Total dead until today in this specific province/country", example = "1200", required = true, position = 7)
    private int latestTotalDead;

    @ApiModelProperty(notes = "The number of dead since yesterday ", example = "25", required = true, position = 8)
    private int diffDeadFromPrevDay;


    @ApiModelProperty(notes = "Total recovered until today in this specific province/country", example = "1200", required = true, position = 9)
    private int latestTotalRecovered;

    @ApiModelProperty(notes = "The number of recovered since yesterday ", example = "25", required = true, position = 10)
    private int diffRecoveredFromPrevDay;

    public ApplicationEntity() {
    }

    public ApplicationEntity(Long id, String provinceState, String countryRegion, String lat, String lon, int latestTotalNewCases, int diffNewCasesFromPrevDay, int latestTotalDead, int diffDeadFromPrevDay, int latestTotalRecovered, int diffRecoveredFromPrevDay) {
        this.id = id;
        this.provinceState = provinceState;
        this.countryRegion = countryRegion;
        this.lat = lat;
        this.lon = lon;
        this.latestTotalNewCases = latestTotalNewCases;
        this.diffNewCasesFromPrevDay = diffNewCasesFromPrevDay;
        this.latestTotalDead = latestTotalDead;
        this.diffDeadFromPrevDay = diffDeadFromPrevDay;
        this.latestTotalRecovered = latestTotalRecovered;
        this.diffRecoveredFromPrevDay = diffRecoveredFromPrevDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getLatestTotalNewCases() {
        return latestTotalNewCases;
    }

    public void setLatestTotalNewCases(int latestTotalNewCases) {
        this.latestTotalNewCases = latestTotalNewCases;
    }

    public int getDiffNewCasesFromPrevDay() {
        return diffNewCasesFromPrevDay;
    }

    public void setDiffNewCasesFromPrevDay(int diffNewCasesFromPrevDay) {
        this.diffNewCasesFromPrevDay = diffNewCasesFromPrevDay;
    }

    public int getLatestTotalDead() {
        return latestTotalDead;
    }

    public void setLatestTotalDead(int latestTotalDead) {
        this.latestTotalDead = latestTotalDead;
    }

    public int getDiffDeadFromPrevDay() {
        return diffDeadFromPrevDay;
    }

    public void setDiffDeadFromPrevDay(int diffDeadFromPrevDay) {
        this.diffDeadFromPrevDay = diffDeadFromPrevDay;
    }

    public int getLatestTotalRecovered() {
        return latestTotalRecovered;
    }

    public void setLatestTotalRecovered(int latestTotalRecovered) {
        this.latestTotalRecovered = latestTotalRecovered;
    }

    public int getDiffRecoveredFromPrevDay() {
        return diffRecoveredFromPrevDay;
    }

    public void setDiffRecoveredFromPrevDay(int diffRecoveredFromPrevDay) {
        this.diffRecoveredFromPrevDay = diffRecoveredFromPrevDay;
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "id=" + id +
                ", provinceState='" + provinceState + '\'' +
                ", countryRegion='" + countryRegion + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", latestTotalNewCases=" + latestTotalNewCases +
                ", diffNewCasesFromPrevDay=" + diffNewCasesFromPrevDay +
                ", latestTotalDead=" + latestTotalDead +
                ", diffDeadFromPrevDay=" + diffDeadFromPrevDay +
                ", latestTotalRecovered=" + latestTotalRecovered +
                ", diffRecoveredFromPrevDay=" + diffRecoveredFromPrevDay +
                '}';
    }
}
