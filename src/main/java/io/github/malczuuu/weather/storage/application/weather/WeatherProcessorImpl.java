package io.github.malczuuu.weather.storage.application.weather;

import io.github.malczuuu.weather.storage.application.settings.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherProcessorImpl implements WeatherProcessor {

  private final SettingService settingService;
  private final WeatherService weatherService;
  private final WeatherClient weatherClient;

  @Override
  public Mono<Long> executeWeatherSync() {
    Flux<WeatherModel> weathers =
        settingService
            .streamTrackedCities()
            .flatMap(weatherClient::getWeather)
            .doOnNext(w -> log.info("Fetched weather for city={}; data={}", w.city(), w))
            .doOnError(e -> log.error("Failed to fetch weather", e))
            .onErrorResume(e -> Flux.empty());
    return weatherService.saveWeatherStream(weathers);
  }
}
