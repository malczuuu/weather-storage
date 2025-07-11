package io.github.malczuuu.weather.storage.application.settings;

import reactor.core.publisher.Flux;

public interface SettingService {

  Flux<String> streamTrackedCities();
}
