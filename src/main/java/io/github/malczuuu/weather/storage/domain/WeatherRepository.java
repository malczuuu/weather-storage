package io.github.malczuuu.weather.storage.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WeatherRepository extends ReactiveMongoRepository<WeatherEntity, String> {}
