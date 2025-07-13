package io.github.malczuuu.weather.storage.infrastructure.testcontainers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

public interface OpenWeatherMapAware {

  @DynamicPropertySource
  static void registerWireMockProps(DynamicPropertyRegistry registry) {
    WireMockContainer wireMock = WireMockInstance.getInstance();
    registry.add("openweathermap.api.url", wireMock::getBaseUrl);
  }
}
