package com.faramarz.spring.Covid19RestApi.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ApplicationEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String provinceState;

    private String countryRegion;

    private Long lat;
    private Long lon;




}
