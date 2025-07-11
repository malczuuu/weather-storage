package io.github.malczuuu.weather.storage.application.settings;

import io.github.malczuuu.weather.storage.domain.ParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class SettingServiceImpl implements SettingService {

  private final ParameterRepository parameterRepository;

  @Override
  public Flux<String> streamTrackedCities() {
    return parameterRepository
        .findFirstByCode(ParameterNames.TRACKED_CITIES)
        .flux()
        .flatMap(parameter -> Flux.fromIterable(parameter.getValues()));
  }
}
