package com.cacttus.weatherlookupservice_as.weather.dto;

import lombok.Data;
import java.time.LocalDate;
@Data
public class WeatherRequestDto {

    private String city;
    private String country;
    private double temperature;
    private String description;
    private double windSpeed;
    private Double avgTemperatureByCity;
    private LocalDate timestamp;

}