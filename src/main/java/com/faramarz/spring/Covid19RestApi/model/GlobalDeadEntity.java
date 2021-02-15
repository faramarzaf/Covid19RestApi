package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class GlobalDeadEntity {

    @Id
    private Long id;

    private int totalReportedDead;
    private int totalDeadToday;


    public GlobalDeadEntity() {
    }

    public GlobalDeadEntity(Long id, Integer totalReportedDead, Integer totalDeadToday) {
        this.id = id;
        this.totalReportedDead = totalReportedDead;
        this.totalDeadToday = totalDeadToday;
    }

    @JsonIgnore
    @JsonProperty(value = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalReportedDead() {
        return totalReportedDead;
    }

    public void setTotalReportedDead(Integer totalReportedDead) {
        this.totalReportedDead = totalReportedDead;
    }

    public Integer getTotalDeadToday() {
        return totalDeadToday;
    }

    public void setTotalDeadToday(Integer totalDeadToday) {
        this.totalDeadToday = totalDeadToday;
    }
}
