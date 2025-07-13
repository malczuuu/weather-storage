package io.github.malczuuu.weather.storage.infrastructure;

import io.github.malczuuu.weather.storage.application.weather.WeatherProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherScheduler {

  private final WeatherProcessor weatherProcessor;

  @Scheduled(
      initialDelayString = "${weather.scheduler.initial-delay}",
      fixedDelayString = "${weather.scheduler.fixed-delay}")
  public void fetchWeather() {
    Long count = weatherProcessor.executeWeatherSync().block();
    log.info("Synchronized count={} weather records", count);
  }
}
