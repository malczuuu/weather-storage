package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Coord(@JsonProperty("lon") Double lon, @JsonProperty("lat") Double lat) {}
