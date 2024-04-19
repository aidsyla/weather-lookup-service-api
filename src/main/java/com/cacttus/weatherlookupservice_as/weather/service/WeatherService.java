package com.cacttus.weatherlookupservice_as.weather.service;

import com.cacttus.weatherlookupservice_as.weather.model.Weather;
import com.cacttus.weatherlookupservice_as.weather.repository.WeatherRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather createWeather(String city, String country, double temperature, String description, double windSpeed,
                                 double avgTempByCity, LocalDate timestamp) {
        Weather weather = new Weather();
        weather.setCity(city);
        weather.setCountry(country);
        weather.setTemperature(temperature);
        weather.setDescription(description);
        weather.setWindSpeed(windSpeed);
        weather.setAvgTemperatureByCity(avgTempByCity);
        weather.setTimestamp(timestamp);
        return weatherRepository.save(weather);
    }

    public List<Weather> findByCity(String city) {
        return weatherRepository.findByCity(city);
    }

    public List<Weather> findByTemperatureBetween(double minTemperature, double maxTemperature) {
        return weatherRepository.findByTemperatureBetween(minTemperature, maxTemperature);
    }

    public Weather findLatestWeatherByCity(String city) {
        return weatherRepository.findTopByCityOrderByTimestampDesc(city);
    }

    public Double findAvgTemperatureByCityAndTimestampBetween(String city, LocalDate startDate, LocalDate endDate) {
        return weatherRepository.avgTemperatureByCityAndTimestampBetween(city, startDate, endDate);
    }

    public Long countByCity(String city) {
        return weatherRepository.countByCity(city);
    }


    //@Scheduled(cron = "0 0 */12 ? * *") // Every 12 hours
    // Qdo 10 minuta mbushet databaza prej fajllit weather.csv qe permban te dhenat per motin
    @Scheduled(fixedRate = 600000) // Every 10 minutes
    public void updateWeather() throws IOException, CsvValidationException {
        List<Weather> weatherList = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream("/weather.csv");
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();

        csvReader.skip(1);

        String[] line;
        while ((line = csvReader.readNext()) != null) {
            Weather weather = new Weather();
            weather.setCity(line[0]);
            weather.setCountry(line[1]);
            weather.setTemperature(Double.parseDouble(line[2]));
            weather.setDescription(line[3]);
            weather.setWindSpeed(Double.parseDouble(line[4]));
            weather.setAvgTemperatureByCity(Double.parseDouble(line[5]));
            weather.setTimestamp(LocalDate.parse(line[6]));
            weatherList.add(weather);
        }
        weatherRepository.saveAll(weatherList);
    }
}