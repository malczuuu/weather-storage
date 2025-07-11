package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Sys(
    @JsonProperty("type") Integer type,
    @JsonProperty("id") Long id,
    @JsonProperty("country") String country,
    @JsonProperty("sunrise") Long sunrise,
    @JsonProperty("sunset") Long sunset) {}
