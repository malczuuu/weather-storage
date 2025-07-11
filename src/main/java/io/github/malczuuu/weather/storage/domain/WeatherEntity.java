package io.github.malczuuu.weather.storage.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@ToString
@Document(collection = "weather")
public class WeatherEntity {

  @MongoId(targetType = FieldType.OBJECT_ID)
  private String id;

  @Field(name = "city")
  private String city;

  @Field(name = "country")
  private String country;

  @Field(name = "longitude")
  private Double longitude;

  @Field(name = "latitude")
  private Double latitude;

  @Field(name = "summary")
  private String summary;

  @Field(name = "description")
  private String description;

  @Field(name = "clouds")
  private Integer clouds;

  @Field(name = "temperature")
  private TemperatureEntity temperature;

  @Field(name = "pressure")
  private PressureEntity pressure;

  @Field(name = "wind")
  private WindEntity wind;

  @Field(name = "timestamp")
  private Instant timestamp;

  @Field(name = "sunrise")
  private Instant sunrise;

  @Field(name = "sunset")
  private Instant sunset;

  public WeatherEntity() {}

  public WeatherEntity(
      String city,
      String country,
      Double longitude,
      Double latitude,
      String summary,
      String description,
      Integer clouds,
      TemperatureEntity temperature,
      PressureEntity pressure,
      WindEntity wind,
      Instant timestamp,
      Instant sunrise,
      Instant sunset) {
    this(
        null,
        city,
        country,
        longitude,
        latitude,
        summary,
        description,
        clouds,
        temperature,
        pressure,
        wind,
        timestamp,
        sunrise,
        sunset);
  }

  public WeatherEntity(
      String id,
      String city,
      String country,
      Double longitude,
      Double latitude,
      String summary,
      String description,
      Integer clouds,
      TemperatureEntity temperature,
      PressureEntity pressure,
      WindEntity wind,
      Instant timestamp,
      Instant sunrise,
      Instant sunset) {
    this.id = id;
    this.city = city;
    this.country = country;
    this.longitude = longitude;
    this.latitude = latitude;
    this.summary = summary;
    this.description = description;
    this.clouds = clouds;
    this.temperature = temperature;
    this.pressure = pressure;
    this.wind = wind;
    this.timestamp = timestamp;
    this.sunrise = sunrise;
    this.sunset = sunset;
  }
}
