package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;

record Clouds(@JsonProperty("all") Integer all) {}
