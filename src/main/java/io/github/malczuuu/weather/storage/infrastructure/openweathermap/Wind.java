package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Wind(
    @JsonProperty("speed") Double speed,
    @JsonProperty("deg") Integer deg,
    @JsonProperty("gust") Double gust) {}
