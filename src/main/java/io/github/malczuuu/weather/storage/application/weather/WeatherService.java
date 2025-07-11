package io.github.malczuuu.weather.storage.application.weather;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WeatherService {

  Mono<Long> saveWeatherStream(Flux<WeatherModel> weatherStream);
}
