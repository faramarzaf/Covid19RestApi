package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GlobalRecoveredEntity {

    @Id
    private Long id;

    private int totalReportedRecovered;
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
