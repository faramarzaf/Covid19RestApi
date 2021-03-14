package com.faramarz.spring.Covid19RestApi.other;


public class Constants {

    private static final String BASE_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/";
    public static final String URL_CONFIRMED = BASE_URL + "time_series_covid19_confirmed_global.csv";
    public static final String URL_DEAD = BASE_URL + "time_series_covid19_deaths_global.csv";
    public static final String URL_RECOVERED = BASE_URL + "time_series_covid19_recovered_global.csv";

}
