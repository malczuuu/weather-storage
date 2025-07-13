package io.github.malczuuu.weather.storage.application.settings;

import io.github.malczuuu.weather.storage.domain.ParameterEntity;
import io.github.malczuuu.weather.storage.domain.ParameterRepository;
import java.util.List;
import reactor.core.publisher.Mono;

public final class ParameterSupport {

  public static void setTrackedCities(ParameterRepository parameterRepository, String... cities) {
    setTrackedCities(parameterRepository, List.of(cities));
  }

  public static void setTrackedCities(
      ParameterRepository parameterRepository, List<String> cities) {
    parameterRepository
        .findFirstByCode(ParameterNames.TRACKED_CITIES)
        .flatMap(
            param -> {
              param.setValues(cities);
              return parameterRepository.save(param);
            })
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  ParameterEntity newParam =
                      new ParameterEntity(ParameterNames.TRACKED_CITIES, cities);
                  return parameterRepository.save(newParam);
                }))
        .block();
  }

  private ParameterSupport() {}
}
