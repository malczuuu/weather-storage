package io.github.malczuuu.weather.storage.application.weather;

import static io.github.malczuuu.weather.storage.application.settings.ParameterSupport.setTrackedCities;
import static io.github.malczuuu.weather.storage.infrastructure.openweathermap.OpenWeatherMapMocking.mockOpenWeatherMapApi;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.weather.storage.domain.*;
import io.github.malczuuu.weather.storage.infrastructure.testcontainers.OpenWeatherMapAware;
import io.github.malczuuu.weather.storage.infrastructure.testcontainers.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles({"test", "openweathermap"})
class WeatherProcessorImplTest implements OpenWeatherMapAware {

  @Autowired private ParameterRepository parameterRepository;
  @Autowired private WeatherRepository weatherRepository;

  @Autowired private WeatherProcessorImpl weatherScheduler;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void givenConfiguredCity_whenExecutingScheduleRun_shouldPersistWeather()
      throws JsonProcessingException {
    setTrackedCities(parameterRepository, "Krakow");
    weatherRepository.deleteAll().block();
    WeatherModel expectedWeather = mockOpenWeatherMapApi(objectMapper);

    Long count = weatherScheduler.executeWeatherSync().blockOptional().orElse(0L);

    Flux<WeatherEntity> weathers = weatherRepository.findAll();

    assertThat(count).isEqualTo(1L);
    StepVerifier.create(weathers)
        .assertNext(weather -> assertEntityWithWeather(weather, expectedWeather))
        .verifyComplete();
  }

  private void assertEntityWithWeather(WeatherEntity weather, WeatherModel expectedWeather) {
    assertThat(weather.getCity()).isEqualTo(expectedWeather.city());
    assertThat(weather.getCountry()).isEqualTo(expectedWeather.country());
    assertThat(weather.getLongitude()).isEqualTo(expectedWeather.longitude());
    assertThat(weather.getLatitude()).isEqualTo(expectedWeather.latitude());
    assertThat(weather.getSummary()).isEqualTo(expectedWeather.summary());
    assertThat(weather.getDescription()).isEqualTo(expectedWeather.description());
    assertThat(weather.getClouds()).isEqualTo(expectedWeather.clouds());
    assertThat(weather.getTemperature().getTemperature())
        .isEqualTo(expectedWeather.temperature().temperature());
    assertThat(weather.getTemperature().getFeelsLike())
        .isEqualTo(expectedWeather.temperature().feelsLike());
    assertThat(weather.getTemperature().getMin()).isEqualTo(expectedWeather.temperature().min());
    assertThat(weather.getTemperature().getMax()).isEqualTo(expectedWeather.temperature().max());
    assertThat(weather.getPressure().getPressure())
        .isEqualTo(expectedWeather.pressure().pressure());
    assertThat(weather.getPressure().getSeaLevel())
        .isEqualTo(expectedWeather.pressure().seaLevel());
    assertThat(weather.getPressure().getGroundLevel())
        .isEqualTo(expectedWeather.pressure().groundLevel());
    assertThat(weather.getWind().getGust()).isEqualTo(expectedWeather.wind().gust());
    assertThat(weather.getWind().getDegree()).isEqualTo(expectedWeather.wind().degree());
    assertThat(weather.getWind().getSpeed()).isEqualTo(expectedWeather.wind().speed());
    assertThat(weather.getTimestamp()).isEqualTo(expectedWeather.timestamp());
    assertThat(weather.getSunrise()).isEqualTo(expectedWeather.sunrise());
    assertThat(weather.getSunset()).isEqualTo(expectedWeather.sunset());
  }
}
