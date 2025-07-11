package io.github.malczuuu.weather.storage.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@ToString
public class WindEntity {

  @Field(name = "speed")
  private Double speed;

  @Field(name = "degree")
  private Integer degree;

  @Field(name = "gust")
  private Double gust;

  public WindEntity() {}

  public WindEntity(Double speed, Integer degree, Double gust) {
    this.speed = speed;
    this.degree = degree;
    this.gust = gust;
  }
}
