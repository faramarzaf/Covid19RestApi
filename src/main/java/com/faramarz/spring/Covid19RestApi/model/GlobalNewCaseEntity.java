package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GlobalNewCaseEntity {

    @Id
    private Long id;

    private int totalReportedNewCases;
    private int totalNewCasesToday;


    public GlobalNewCaseEntity() {
    }

    public GlobalNewCaseEntity(Long id, Integer totalReportedNewCases, Integer totalNewCasesToday) {
        this.id = id;
        this.totalReportedNewCases = totalReportedNewCases;
        this.totalNewCasesToday = totalNewCasesToday;
    }

    @JsonIgnore
    @JsonProperty(value = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalReportedNewCases() {
        return totalReportedNewCases;
    }

    public void setTotalReportedNewCases(Integer totalReportedDead) {
        this.totalReportedNewCases = totalReportedDead;
    }

    public Integer getTotalNewCasesToday() {
        return totalNewCasesToday;
    }

    public void setTotalNewCasesToday(Integer totalDeadToday) {
        this.totalNewCasesToday = totalDeadToday;
    }
}
