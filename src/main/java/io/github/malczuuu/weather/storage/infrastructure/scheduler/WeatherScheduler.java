package io.github.malczuuu.weather.storage.infrastructure.scheduler;

import io.github.malczuuu.weather.storage.application.weather.WeatherProcessorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherScheduler {

  private final WeatherProcessorImpl weatherProcessor;

  @Scheduled(initialDelayString = "30s", fixedDelayString = "30s")
  public void fetchWeather() {
    weatherProcessor
        .executeWeatherSync()
        .subscribe(count -> log.info("Synchronized count={} weather records", count));
  }
}
