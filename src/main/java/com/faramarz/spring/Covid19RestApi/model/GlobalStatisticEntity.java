package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ApiModel(description = "Class representing a GlobalEntity by the application.")
@JsonPropertyOrder({"id", "totalReportedDead", "totalDeadToday", "totalReportedNewCases", "totalNewCasesToday",
        "totalReportedRecovered", "totalRecoveredToday"})
public class GlobalStatisticEntity {

    // TODO maybe for id in JsonPropertyOrder exception

    @Id
    private Long id;

    @ApiModelProperty(notes = "Total dead case until today", example = "1243570", required = true, position = 1)
    private int totalReportedDead;

    @ApiModelProperty(notes = "Total dead case in today", example = "12435", required = true, position = 2)
    private int totalDeadToday;

    @ApiModelProperty(notes = "Total new case (confirmed/reported) until today", example = "12543570", required = true, position = 3)
    private int totalReportedNewCases;

    @ApiModelProperty(notes = "Total new case (confirmed/reported) in today", example = "1236570", required = true, position = 4)
    private int totalNewCasesToday;

    @ApiModelProperty(notes = "Total recovered case until today", example = "132470", required = true, position = 5)
    private int totalReportedRecovered;

    @ApiModelProperty(notes = "Total recovered case in today", example = "124570", required = true, position = 6)
    private int totalRecoveredToday;

    public GlobalStatisticEntity() {
    }

    public GlobalStatisticEntity(Long id, int totalReportedDead, int totalDeadToday, int totalReportedNewCases, int totalNewCasesToday, int totalReportedRecovered, int totalRecoveredToday) {
        this.id = id;
        this.totalReportedDead = totalReportedDead;
        this.totalDeadToday = totalDeadToday;
        this.totalReportedNewCases = totalReportedNewCases;
        this.totalNewCasesToday = totalNewCasesToday;
        this.totalReportedRecovered = totalReportedRecovered;
        this.totalRecoveredToday = totalRecoveredToday;
    }

    @JsonIgnore
    @JsonProperty(value = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalReportedDead() {
        return totalReportedDead;
    }

    public void setTotalReportedDead(int totalReportedDead) {
        this.totalReportedDead = totalReportedDead;
    }

    public int getTotalDeadToday() {
        return totalDeadToday;
    }

    public void setTotalDeadToday(int totalDeadToday) {
        this.totalDeadToday = totalDeadToday;
    }

    public int getTotalReportedNewCases() {
        return totalReportedNewCases;
    }

    public void setTotalReportedNewCases(int totalReportedNewCases) {
        this.totalReportedNewCases = totalReportedNewCases;
    }

    public int getTotalNewCasesToday() {
        return totalNewCasesToday;
    }

    public void setTotalNewCasesToday(int totalNewCasesToday) {
        this.totalNewCasesToday = totalNewCasesToday;
    }

    public int getTotalReportedRecovered() {
        return totalReportedRecovered;
    }

    public void setTotalReportedRecovered(int totalReportedRecovered) {
        this.totalReportedRecovered = totalReportedRecovered;
    }

    public int getTotalRecoveredToday() {
        return totalRecoveredToday;
    }

    public void setTotalRecoveredToday(int totalRecoveredToday) {
        this.totalRecoveredToday = totalRecoveredToday;
    }

    @Override
    public String toString() {
        return "GlobalStatisticEntity{" +
                "id=" + id +
                ", totalReportedDead=" + totalReportedDead +
                ", totalDeadToday=" + totalDeadToday +
                ", totalReportedNewCases=" + totalReportedNewCases +
                ", totalNewCasesToday=" + totalNewCasesToday +
                ", totalReportedRecovered=" + totalReportedRecovered +
                ", totalRecoveredToday=" + totalRecoveredToday +
                '}';
    }
}
