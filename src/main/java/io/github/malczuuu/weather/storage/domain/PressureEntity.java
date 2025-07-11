package io.github.malczuuu.weather.storage.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@ToString
public class PressureEntity {

  @Field(name = "pressure")
  private Integer pressure;

  @Field(name = "seaLevel")
  private Integer seaLevel;

  @Field(name = "groundLevel")
  private Integer groundLevel;

  public PressureEntity() {}

  public PressureEntity(Integer pressure, Integer seaLevel, Integer groundLevel) {
    this.pressure = pressure;
    this.seaLevel = seaLevel;
    this.groundLevel = groundLevel;
  }
}
