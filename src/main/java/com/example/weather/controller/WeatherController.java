package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    // Constructor injection of WeatherService
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Endpoint to get current weather for a city
    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        try {
            var result = weatherService.getCurrentTemperature(city);
            // Return the weather data as JSON
            return ResponseEntity.ok(Map.of(
                    "city", city,
                    "temperature", result.getTemperature(),
                    "unit", "C",
                    "mock", result.isMock()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Unable to fetch weather", "details", e.getMessage()));
        }
    }
}
