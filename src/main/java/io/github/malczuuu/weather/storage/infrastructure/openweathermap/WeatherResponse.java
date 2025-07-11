package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

record WeatherResponse(
    @JsonProperty("coord") Coord coord,
    @JsonProperty("weather") List<Weather> weather,
    @JsonProperty("base") String base,
    @JsonProperty("main") Main main,
    @JsonProperty("visibility") Integer visibility,
    @JsonProperty("wind") Wind wind,
    @JsonProperty("clouds") Clouds clouds,
    @JsonProperty("dt") Long dt,
    @JsonProperty("sys") Sys sys,
    @JsonProperty("timezone") Integer timezone,
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("cod") Integer cod) {}
