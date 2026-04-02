package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final boolean mockMode;

    public WeatherService(RestTemplate restTemplate,
                          @Value("${OPENWEATHER_API_KEY:}") String apiKey,
                          @Value("${WEATHER_MOCK:false}") boolean mockMode) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.mockMode = mockMode;
    }
    public WeatherResult getCurrentTemperature(String city) {
        // If API key is missing
        if (apiKey == null || apiKey.isBlank()) {
            if (mockMode) {
                return new WeatherResult(fakeTempFor(city), true);
            }
            throw new IllegalStateException("Missing OPENWEATHER_API_KEY environment variable");
        }

        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city + ",IN")
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .toUriString();

        try {
            Map response = restTemplate.getForObject(url, Map.class);
            if (response == null) {
                throw new RuntimeException("Empty response from weather provider");
            }

            Map main = (Map) response.get("main");
            if (main == null || main.get("temp") == null) {
                throw new RuntimeException("Temperature data not present in response");
            }

            Object tempObj = main.get("temp");
            double temp;
            if (tempObj instanceof Number) {
                temp = ((Number) tempObj).doubleValue();
            } else {
                temp = Double.parseDouble(tempObj.toString());
            }
            return new WeatherResult(temp, false);
        } catch (HttpClientErrorException e) {
            // API returned 4xx (including 401)
            if (mockMode) {
                return new WeatherResult(fakeTempFor(city), true);
            }
            throw new RuntimeException("Unable to fetch weather", e);
        } catch (Exception e) {
            if (mockMode) {
                return new WeatherResult(fakeTempFor(city), true);
            }
            throw new RuntimeException("Unable to fetch weather", e);
        }
    }

    private double fakeTempFor(String city) {
        // deterministic fake temps for common Indian cities
        return switch (city.toLowerCase()) {
            case "delhi" -> 30.5;
            case "mumbai" -> 28.2;
            case "bengaluru", "bangalore" -> 24.1;
            case "chennai" -> 32.0;
            case "kolkata" -> 29.0;
            case "hyderabad" -> 27.5;
            case "pune" -> 26.3;
            case "ahmedabad" -> 33.1;
            case "jaipur" -> 31.0;
            case "lucknow" -> 29.4;
            default -> 25.0;
        };
    }
}
