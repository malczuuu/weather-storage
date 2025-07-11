package io.github.malczuuu.weather.storage.application.weather;

import io.github.malczuuu.weather.storage.domain.PressureEntity;
import io.github.malczuuu.weather.storage.domain.TemperatureEntity;
import io.github.malczuuu.weather.storage.domain.WeatherEntity;
import io.github.malczuuu.weather.storage.domain.WindEntity;

public class WeatherMapper {

  public WeatherEntity toEntity(WeatherModel model) {
    return new WeatherEntity(
        model.city(),
        model.country(),
        model.longitude(),
        model.latitude(),
        model.summary(),
        model.description(),
        model.clouds(),
        new TemperatureEntity(
            model.temperature().temperature(),
            model.temperature().feelsLike(),
            model.temperature().min(),
            model.temperature().max()),
        new PressureEntity(
            model.pressure().pressure(),
            model.pressure().seaLevel(),
            model.pressure().groundLevel()),
        new WindEntity(model.wind().speed(), model.wind().degree(), model.wind().gust()),
        model.timestamp(),
        model.sunrise(),
        model.sunset());
  }
}
