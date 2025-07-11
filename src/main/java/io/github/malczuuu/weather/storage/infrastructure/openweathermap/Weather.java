package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Weather(
    @JsonProperty("id") Long id,
    @JsonProperty("main") String main,
    @JsonProperty("description") String description,
    @JsonProperty("icon") String icon) {}
