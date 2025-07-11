package io.github.malczuuu.weather.storage.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ParameterRepository extends ReactiveMongoRepository<ParameterEntity, String> {

  Mono<ParameterEntity> findFirstByCode(String code);
}
