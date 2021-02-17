package com.faramarz.spring.Covid19RestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "Class representing a GlobalDeadEntity by the application.")
public class GlobalDeadEntity {

    @Id
    private Long id;

    @ApiModelProperty(notes = "Total dead case until today", example = "1243570", required = true, position = 1)
    private int totalReportedDead;

    @ApiModelProperty(notes = "Total dead case in today", example = "12435", required = true, position = 2)
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
