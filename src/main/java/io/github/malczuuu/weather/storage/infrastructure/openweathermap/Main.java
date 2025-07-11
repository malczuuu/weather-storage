package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Main(
    @JsonProperty("temp") Double temp,
    @JsonProperty("feels_like") Double feelsLike,
    @JsonProperty("temp_min") Double tempMin,
    @JsonProperty("temp_max") Double tempMax,
    @JsonProperty("pressure") Integer pressure,
    @JsonProperty("humidity") Integer humidity,
    @JsonProperty("sea_level") Integer seaLevel,
    @JsonProperty("grnd_level") Integer grndLevel) {}
