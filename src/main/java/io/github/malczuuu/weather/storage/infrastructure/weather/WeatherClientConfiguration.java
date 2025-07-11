package io.github.malczuuu.weather.storage.infrastructure.weather;

import io.github.malczuuu.weather.storage.application.weather.WeatherClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WeatherClientConfiguration {

  @ConditionalOnMissingBean(WeatherClient.class)
  @Bean
  public WeatherClient weatherClient() {
    log.warn("WeatherClient not found - fallback to NoOp");
    return city -> Mono.empty();
  }
}
