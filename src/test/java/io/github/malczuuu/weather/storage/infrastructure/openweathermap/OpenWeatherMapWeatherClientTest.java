package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import static io.github.malczuuu.weather.storage.infrastructure.openweathermap.OpenWeatherMapMocking.mockOpenWeatherMapApi;
import static io.github.malczuuu.weather.storage.infrastructure.openweathermap.OpenWeatherMapMocking.resetOpenWeatherMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.weather.storage.application.weather.WeatherModel;
import io.github.malczuuu.weather.storage.infrastructure.testcontainers.OpenWeatherMapAware;
import io.github.malczuuu.weather.storage.infrastructure.testcontainers.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles({"test", "openweathermap"})
class OpenWeatherMapWeatherClientTest implements OpenWeatherMapAware {

  @Autowired private OpenWeatherMapWeatherClient weatherClient;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void givenOpenWeatherMapMocked_whenRetrievingWeather_shouldReturnWeather()
      throws JsonProcessingException {
    WeatherModel expectedWeather = mockOpenWeatherMapApi(objectMapper);

    Mono<WeatherModel> weather = weatherClient.getWeather("Krakow");

    StepVerifier.create(weather).expectNext(expectedWeather).verifyComplete();
  }

  @Test
  void givenOpenWeatherMapNotMocked_whenRetrievingWeather_shouldReturnEmptyStreamOfWeathers() {
    resetOpenWeatherMap();

    Mono<WeatherModel> weather = weatherClient.getWeather("Krakow");

    StepVerifier.create(weather).expectNextCount(0L).verifyComplete();
  }
}
