package io.github.malczuuu.weather.storage.infrastructure.openweathermap;

import io.github.malczuuu.weather.storage.application.weather.PressureModel;
import io.github.malczuuu.weather.storage.application.weather.TemperatureModel;
import io.github.malczuuu.weather.storage.application.weather.WeatherClient;
import io.github.malczuuu.weather.storage.application.weather.WeatherModel;
import io.github.malczuuu.weather.storage.application.weather.WindModel;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@ConditionalOnProperty("openweathermap.enabled")
public class OpenWeatherMapWeatherClient implements WeatherClient {

  private final WebClient client;

  private final String weatherEndpoint;
  private final String token;

  public OpenWeatherMapWeatherClient(
      WebClient client,
      @Value("${openweathermap.api.endpoint.weather}") String weatherEndpoint,
      @Value("${openweathermap.api.token}") String token) {
    this.client = client;
    this.weatherEndpoint = weatherEndpoint;
    this.token = token;
  }

  @Override
  public Mono<WeatherModel> getWeather(String city) {
    String withQueryString = weatherEndpoint + "?q={city}&appid={appid}&units={units}&lang={lang}";
    Map<String, String> query =
        Map.of("city", city, "appid", token, "units", "metric", "lang", "en");

    Mono<WeatherResponse> response =
        client
            .get()
            .uri(withQueryString, query)
            .retrieve()
            .bodyToMono(WeatherResponse.class)
            .doOnError(this::logErrorOpenWeatherApiCall)
            .onErrorResume(e -> Mono.empty());

    return response.map(this::toWeatherModel);
  }

  private void logErrorOpenWeatherApiCall(Throwable e) {
    log.error("Unable to fetch weather from OpenWeatherMap API; reason={}", e.getMessage(), e);
  }

  private WeatherModel toWeatherModel(WeatherResponse response) {
    return new WeatherModel(
        response.name(),
        response.sys() != null ? response.sys().country() : null,
        response.coord() != null ? response.coord().lon() : null,
        response.coord() != null ? response.coord().lat() : null,
        Optional.ofNullable(response.weather()).stream()
            .flatMap(List::stream)
            .findFirst()
            .map(Weather::main)
            .orElse(null),
        Optional.ofNullable(response.weather()).stream()
            .flatMap(List::stream)
            .findFirst()
            .map(Weather::description)
            .orElse(null),
        response.clouds() != null ? response.clouds().all() : null,
        new TemperatureModel(
            response.main() != null ? response.main().temp() : null,
            response.main() != null ? response.main().feelsLike() : null,
            response.main() != null ? response.main().tempMin() : null,
            response.main() != null ? response.main().tempMax() : null),
        new PressureModel(
            response.main() != null ? response.main().pressure() : null,
            response.main() != null ? response.main().seaLevel() : null,
            response.main() != null ? response.main().grndLevel() : null),
        new WindModel(
            response.wind() != null ? response.wind().speed() : null,
            response.wind() != null ? response.wind().deg() : null,
            response.wind() != null ? response.wind().gust() : null),
        response.dt() != null ? Instant.ofEpochSecond(response.dt()) : null,
        response.sys() != null && response.sys().sunrise() != null
            ? Instant.ofEpochSecond(response.sys().sunrise())
            : null,
        response.sys() != null && response.sys().sunset() != null
            ? Instant.ofEpochSecond(response.sys().sunset())
            : null);
  }
}
