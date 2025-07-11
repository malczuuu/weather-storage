package io.github.malczuuu.weather.storage.application.weather;

import reactor.core.publisher.Mono;

public interface WeatherClient {

  Mono<WeatherModel> getWeather(String city);
}
