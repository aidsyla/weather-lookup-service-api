package com.cacttus.weatherlookupservice_as.weather.controller;

import com.cacttus.weatherlookupservice_as.weather.dto.WeatherRequestDto;
import com.cacttus.weatherlookupservice_as.weather.model.Weather;
import com.cacttus.weatherlookupservice_as.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/auth/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/")
    public ResponseEntity<Weather> createWeather(@RequestBody WeatherRequestDto weatherRequest) {
        Weather weather = weatherService.createWeather(
                weatherRequest.getCity(),
                weatherRequest.getCountry(),
                weatherRequest.getTemperature(),
                weatherRequest.getDescription(),
                weatherRequest.getWindSpeed(),
                weatherRequest.getAvgTemperatureByCity(),
                weatherRequest.getTimestamp());
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Weather>> findByCity(@PathVariable String city) {
        List<Weather> weatherList = weatherService.findByCity(city);
        return ResponseEntity.ok(weatherList);
    }

    @GetMapping("/temperature/{minTemperature}/{maxTemperature}")
    public ResponseEntity<List<Weather>> findByTemperatureBetween(@PathVariable double minTemperature, @PathVariable double maxTemperature) {
        List<Weather> weatherList = weatherService.findByTemperatureBetween(minTemperature, maxTemperature);
        return ResponseEntity.ok(weatherList);
    }

    @GetMapping("/city/{city}/latest")
    public ResponseEntity<Weather> findLatestWeatherByCity(@PathVariable String city) {
        Weather weather = weatherService.findLatestWeatherByCity(city);
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/city/{city}/avgTemperature")
    public ResponseEntity<Double> findAvgTemperatureByCityAndTimestampBetween(@PathVariable String city, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate startDateTime = LocalDate.parse(startDate);
        LocalDate endDateTime = LocalDate.parse(endDate);
        Double avgTemperature = weatherService.findAvgTemperatureByCityAndTimestampBetween(city, startDateTime, endDateTime);
        return ResponseEntity.ok(avgTemperature);
    }

    @GetMapping("/city/{city}/count")
    public ResponseEntity<Long> countByCity(@PathVariable String city) {
        Long count = weatherService.countByCity(city);
        return ResponseEntity.ok(count);
    }
}