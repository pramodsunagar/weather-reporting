package com.example.weather.service;

public class WeatherResult {
    private final double temperature;
    private final boolean mock;

    public WeatherResult(double temperature, boolean mock) {
        this.temperature = temperature;
        this.mock = mock;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isMock() {
        return mock;
    }
}
