package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ApiModel(description = "Class representing a GlobalRecoveredEntity by the application.")
public class GlobalRecoveredEntity {

    @Id
    private Long id;

    @ApiModelProperty(notes = "Total recovered case until today", example = "132470", required = true, position = 1)
    private int totalReportedRecovered;

    @ApiModelProperty(notes = "Total recovered case in today", example = "124570", required = true, position = 2)
    private int totalRecoveredToday;


    public GlobalRecoveredEntity() {
    }

    public GlobalRecoveredEntity(Long id, Integer totalReportedRecovered, Integer totalRecoveredToday) {
        this.id = id;
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

    public Integer getTotalReportedRecovered() {
        return totalReportedRecovered;
    }

    public void setTotalReportedRecovered(Integer totalReportedDead) {
        this.totalReportedRecovered = totalReportedDead;
    }

    public Integer getTotalRecoveredToday() {
        return totalRecoveredToday;
    }

    public void setTotalRecoveredToday(Integer totalDeadToday) {
        this.totalRecoveredToday = totalDeadToday;
    }
}
