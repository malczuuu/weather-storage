package io.github.malczuuu.weather.storage.application.weather;

import java.time.Instant;

public record WeatherModel(
    String city,
    String country,
    Double longitude,
    Double latitude,
    String summary,
    String description,
    Integer clouds,
    TemperatureModel temperature,
    PressureModel pressure,
    WindModel wind,
    Instant timestamp,
    Instant sunrise,
    Instant sunset) {}
