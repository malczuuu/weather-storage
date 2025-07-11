package io.github.malczuuu.weather.storage.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@ToString
public class TemperatureEntity {

  @Field(name = "temperature")
  private Double temperature;

  @Field(name = "feelsLike")
  private Double feelsLike;

  @Field(name = "min")
  private Double min;

  @Field(name = "max")
  private Double max;

  public TemperatureEntity() {}

  public TemperatureEntity(Double temperature, Double feelsLike, Double min, Double max) {
    this.temperature = temperature;
    this.feelsLike = feelsLike;
    this.min = min;
    this.max = max;
  }
}
