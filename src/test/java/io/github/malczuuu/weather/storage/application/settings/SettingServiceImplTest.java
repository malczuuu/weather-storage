package io.github.malczuuu.weather.storage.application.settings;

import io.github.malczuuu.weather.storage.domain.ParameterRepository;
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
@ActiveProfiles("test")
class SettingServiceImplTest {

  @Autowired private ParameterRepository parameterRepository;

  @Autowired private SettingServiceImpl settingService;

  @Test
  void givenNoCities_whenRetrievingCitiesSetting_shouldReturnEmptyStream() {
    parameterRepository.deleteAll().block();

    Flux<String> trackedCities = settingService.streamTrackedCities();

    StepVerifier.create(trackedCities).expectNextCount(0L).verifyComplete();
  }

  @Test
  void givenCities_whenRetrievingTrackedCitiesSetting_shouldReturnStreamOfCities() {
    ParameterSupport.setTrackedCities(parameterRepository, "Krakow", "Gliwice", "Gniezno");

    Flux<String> trackedCities = settingService.streamTrackedCities();

    StepVerifier.create(trackedCities).expectNext("Krakow", "Gliwice", "Gniezno").verifyComplete();
  }
}
