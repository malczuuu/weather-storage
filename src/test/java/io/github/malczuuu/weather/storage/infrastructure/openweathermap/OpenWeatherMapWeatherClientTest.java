package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.weather.storage._testcontainers.TestcontainersConfiguration;
import io.github.malczuuu.weather.storage._testcontainers.WireMockInstance;
import io.github.malczuuu.weather.storage.application.weather.PressureModel;
import io.github.malczuuu.weather.storage.application.weather.TemperatureModel;
import io.github.malczuuu.weather.storage.application.weather.WeatherModel;
import io.github.malczuuu.weather.storage.application.weather.WindModel;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(properties = "openweathermap.enabled=true")
class OpenWeatherMapWeatherClientTest {

  @Autowired private ObjectMapper objectMapper;
  @Autowired private WebClient webClient;

  @Autowired private OpenWeatherMapWeatherClient weatherClient;

  @DynamicPropertySource
  static void registerWireMockProps(DynamicPropertyRegistry registry) {
    WireMockContainer wireMock = WireMockInstance.getInstance();
    registry.add("openweathermap.api.url", wireMock::getBaseUrl);
  }

  @Test
  void execute() throws JsonProcessingException {
    WeatherResponse owm = getOpenWeatherMapResponse();

    stubFor(
        get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(okJson(objectMapper.writeValueAsString(owm))));

    Mono<WeatherModel> weather = weatherClient.getWeather("Krakow");

    WeatherModel weatherModel = toWeatherModel(owm);
    StepVerifier.create(weather).expectNext(weatherModel).expectComplete().verify();
  }

  private static WeatherResponse getOpenWeatherMapResponse() {
    return new WeatherResponse(
        new Coord(19.9167, 50.0833),
        List.of(new Weather(801L, "Clouds", "few clouds", "02d")),
        "stations",
        new Main(21.81, 21.43, 21.52, 22.44, 1013, 53, 1013, 983),
        5000,
        new Wind(2.57, 150, 2.33),
        new Clouds(20),
        1752317660L,
        new Sys(2, 2090246L, "PL", 1752288248L, 1752346042L),
        7200,
        1L,
        "Krakow",
        200);
  }

  private static WeatherModel toWeatherModel(WeatherResponse owm) {
    return new WeatherModel(
        owm.name(),
        owm.sys().country(),
        owm.coord().lon(),
        owm.coord().lat(),
        owm.weather().getFirst().main(),
        owm.weather().getFirst().description(),
        owm.clouds().all(),
        new TemperatureModel(
            owm.main().temp(), owm.main().feelsLike(), owm.main().tempMin(), owm.main().tempMax()),
        new PressureModel(owm.main().pressure(), owm.main().seaLevel(), owm.main().grndLevel()),
        new WindModel(owm.wind().speed(), owm.wind().deg(), owm.wind().gust()),
        Instant.ofEpochSecond(owm.dt()),
        Instant.ofEpochSecond(owm.sys().sunrise()),
        Instant.ofEpochSecond(owm.sys().sunset()));
  }
}
