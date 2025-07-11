package io.github.malczuuu.weather.storage.application.weather;

import io.github.malczuuu.weather.storage.domain.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

  private final WeatherRepository weatherRepository;

  private final WeatherMapper weatherMapper = new WeatherMapper();

  @Override
  public Mono<Long> saveWeatherStream(Flux<WeatherModel> weatherStream) {
    return weatherRepository.saveAll(weatherStream.mapNotNull(weatherMapper::toEntity)).count();
  }
}
