package com.cacttus.weatherlookupservice_as.weather.repository;

import com.cacttus.weatherlookupservice_as.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByCity(String city);
    List<Weather> findByTemperatureBetween(double minTemperature, double maxTemperature);
    Weather findTopByCityOrderByTimestampDesc(String city);
    @Query("SELECT AVG(w.temperature) FROM Weather w WHERE w.city = :city AND w.timestamp BETWEEN :startDate AND :endDate")
    Double avgTemperatureByCityAndTimestampBetween(String city, LocalDate startDate, LocalDate endDate);
    Long countByCity(String city);

}